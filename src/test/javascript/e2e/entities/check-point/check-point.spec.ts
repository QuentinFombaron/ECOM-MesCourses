/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CheckPointComponentsPage, CheckPointDeleteDialog, CheckPointUpdatePage } from './check-point.page-object';

const expect = chai.expect;

describe('CheckPoint e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let checkPointUpdatePage: CheckPointUpdatePage;
    let checkPointComponentsPage: CheckPointComponentsPage;
    let checkPointDeleteDialog: CheckPointDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load CheckPoints', async () => {
        await navBarPage.goToEntity('check-point');
        checkPointComponentsPage = new CheckPointComponentsPage();
        expect(await checkPointComponentsPage.getTitle()).to.eq('Check Points');
    });

    it('should load create CheckPoint page', async () => {
        await checkPointComponentsPage.clickOnCreateButton();
        checkPointUpdatePage = new CheckPointUpdatePage();
        expect(await checkPointUpdatePage.getPageTitle()).to.eq('Create or edit a Check Point');
        await checkPointUpdatePage.cancel();
    });

    it('should create and save CheckPoints', async () => {
        const nbButtonsBeforeCreate = await checkPointComponentsPage.countDeleteButtons();

        await checkPointComponentsPage.clickOnCreateButton();
        await checkPointUpdatePage.setHeureInput('heure');
        expect(await checkPointUpdatePage.getHeureInput()).to.eq('heure');
        await checkPointUpdatePage.setUserInput('5');
        expect(await checkPointUpdatePage.getUserInput()).to.eq('5');
        await checkPointUpdatePage.setCourseInput('5');
        expect(await checkPointUpdatePage.getCourseInput()).to.eq('5');
        await checkPointUpdatePage.save();
        expect(await checkPointUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await checkPointComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last CheckPoint', async () => {
        const nbButtonsBeforeDelete = await checkPointComponentsPage.countDeleteButtons();
        await checkPointComponentsPage.clickOnLastDeleteButton();

        checkPointDeleteDialog = new CheckPointDeleteDialog();
        expect(await checkPointDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Check Point?');
        await checkPointDeleteDialog.clickOnConfirmButton();

        expect(await checkPointComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
