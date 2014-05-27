package com.revimedia.tests.cds.auto.mfs;import com.revimedia.testing.beans.auto.LeadDataType;import com.revimedia.testing.beans.auto.QuoteRequestType;import com.revimedia.testing.beans.auto.VehicleType;import com.revimedia.testing.cds.auto.mfs.pages.CompareAndSavePage;import com.revimedia.testing.cds.auto.mfs.pages.DriverPage;import com.revimedia.testing.cds.auto.mfs.pages.VehiclePage;import com.revimedia.testing.cds.auto.staticdata.StaticDataAutoMFS;import com.revimedia.testing.cds.auto.staticdata.SurveyPath;import com.revimedia.testing.configuration.dto.Contact;import com.revimedia.testing.configuration.helpers.Formatter;import com.revimedia.testing.configuration.proxy.HarParser;import com.revimedia.testing.configuration.proxy.Submit;import com.revimedia.testing.configuration.utils.XmlToObject;import com.revimedia.tests.configuration.BaseTest;import com.revimedia.tests.configuration.dataproviders.AutoDataProvider;import com.revimedia.tests.configuration.helpers.SubmitVerifier;import net.lightbody.bmp.core.har.HarEntry;import org.testng.annotations.Test;import java.util.List;import static org.hamcrest.MatcherAssert.assertThat;import static org.hamcrest.Matchers.*;/** * Created by Funker on 23.04.14. */public class SubmitTests extends BaseTest {    public DriverPage driverPage;    public VehiclePage vehiclePage;    public CompareAndSavePage compareAndSavePage;    @Test(groups = {"submit"}, enabled = true, dataProvider = "contactAndStaticDataAutoMFS", dataProviderClass = AutoDataProvider.class)    public void mainPositiveSubmitCampaign(Contact contact, StaticDataAutoMFS staticData) throws Exception {        driverPage = new DriverPage(driver);        driverPage.fillInAllFields(contact, staticData);        assertThat(driverPage.getPageText(), containsString(contact.getCity()));        vehiclePage = driverPage.clickOnContinue();        compareAndSavePage = vehiclePage.fillInAllFields(staticData).clickOnContinue();        compareAndSavePage.fillInAllFields(contact, staticData);        assertThat(compareAndSavePage.getZipStateAndCity(), containsString(contact.getCity() + ", " + contact.getState()));        compareAndSavePage.submitForm();        Submit submit = HarParser.getSubmit();        // Assert response, step 1        SubmitVerifier.verifyResponse(submit.getResponse());        LeadDataType leadDataType = XmlToObject.unMarshal(LeadDataType.class, submit.getRequest());        String leadId = leadDataType.getAffiliateData().getLeadId();        // Assert request, step 2        assertThat(leadId, is(notNullValue()));        assertThat(leadId.length(), is(36));        SubmitVerifier.verifyRequest(contact, leadDataType);        SubmitVerifier.verifyRequest(staticData, leadDataType);    }    @Test(groups = {"submit", "polk"}, enabled = true, dataProvider = "contactAndStaticDataAutoMFS", dataProviderClass = AutoDataProvider.class)    public void shouldPresentInInURLPolkData(Contact contact, StaticDataAutoMFS staticData) throws Exception {        driverPage = new DriverPage(driver);        vehiclePage = driverPage.fillInAllFields(contact, staticData).clickOnContinue();        vehiclePage.fillInAllFields(staticData);        List<HarEntry> polkData = HarParser.getPolkData();        String make = polkData.get(0).getResponse().getContent().getText();        String model = polkData.get(1).getResponse().getContent().getText();        // Asserts URL        assertThat(polkData.get(0).getRequest().getUrl(), containsString("polk?year=" + staticData.getYear()));        assertThat(polkData.get(1).getRequest().getUrl(), containsString("polk?year=" + staticData.getYear() + "&make=" + staticData.getMake()));        //Asserts values that displayed in drop downs        assertThat(Formatter.itemsJSONToList(make), containsInAnyOrder((vehiclePage.getAllMakedCarsList()).toArray()));        assertThat(Formatter.itemsJSONToList(model), equalTo(vehiclePage.getAllModelsList()));    }    @Test(groups = {"submit", "survey path", "default alias answer"}, dataProvider = "contactAndStaticDataAutoMFS", dataProviderClass = AutoDataProvider.class)    public void DRAFT_shoulBePresentInResponseXMLSurveyPathAndDefaultAliasAnswers(Contact contact, StaticDataAutoMFS staticData) throws Exception {        driverPage = new DriverPage(driver);        vehiclePage = driverPage.fillInAllFields(contact, staticData).clickOnContinue();        compareAndSavePage = vehiclePage.fillInAllFields(staticData).clickOnContinue();        compareAndSavePage.fillInAllFields(contact, staticData).submitForm();        LeadDataType leadDataType = XmlToObject.unMarshal(LeadDataType.class, HarParser.getSubmit().getRequest());        String surveyPath = leadDataType.getAffiliateData().getSurveyPath();        QuoteRequestType quoteRequest = leadDataType.getQuoteRequest();        VehicleType vehicle = leadDataType.getQuoteRequest().getVehicles().getVehicle().get(0);        // Assert survey Path        assertThat(SurveyPath.AUTO_MFS, is(surveyPath));        // Assert all  Default Alias Answers for this campaign        assertThat(vehicle.getAnnualMiles(), is("12500"));        assertThat(vehicle.getComphrensiveDeductible(), is("500"));        assertThat(vehicle.getCollisionDeductible(), is("500"));        assertThat(vehicle.getGarage(), is("No Cover"));        assertThat(vehicle.getPrimaryUse(), is("Commute To/From Work"));        assertThat(vehicle.getOneWayDistance(), is("10-19"));        assertThat(quoteRequest.getDrivers().getDriver().getAgeLicensed(), is("16"));        assertThat(quoteRequest.getDrivers().getDriver().getYearsAtEmployer(), is("5"));        assertThat(quoteRequest.getDrivers().getDriver().getOccupation(), is("Employeed"));        assertThat(quoteRequest.getInsurance().getRequestedPolicy().getCoverageType(), is("Standard Protection"));    }}