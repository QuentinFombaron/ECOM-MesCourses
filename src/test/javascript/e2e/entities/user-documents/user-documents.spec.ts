/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UserDocumentsComponentsPage, UserDocumentsDeleteDialog, UserDocumentsUpdatePage } from './user-documents.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('UserDocuments e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let userDocumentsUpdatePage: UserDocumentsUpdatePage;
    let userDocumentsComponentsPage: UserDocumentsComponentsPage;
    let userDocumentsDeleteDialog: UserDocumentsDeleteDialog;
    const fileToUpload = '../../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load UserDocuments', async () => {
        await navBarPage.goToEntity('user-documents');
        userDocumentsComponentsPage = new UserDocumentsComponentsPage();
        expect(await userDocumentsComponentsPage.getTitle()).to.eq('User Documents');
    });

    it('should load create UserDocuments page', async () => {
        await userDocumentsComponentsPage.clickOnCreateButton();
        userDocumentsUpdatePage = new UserDocumentsUpdatePage();
        expect(await userDocumentsUpdatePage.getPageTitle()).to.eq('Create or edit a User Documents');
        await userDocumentsUpdatePage.cancel();
    });

    it('should create and save UserDocuments', async () => {
        const nbButtonsBeforeCreate = await userDocumentsComponentsPage.countDeleteButtons();

        await userDocumentsComponentsPage.clickOnCreateButton();
        await userDocumentsUpdatePage.setDateDeNaissanceInput('2000-12-31');
        expect(await userDocumentsUpdatePage.getDateDeNaissanceInput()).to.eq('2000-12-31');
        await userDocumentsUpdatePage.sexeSelectLastOption();
        await userDocumentsUpdatePage.setCertificatMedicalInput(absolutePath);
        await userDocumentsUpdatePage.setLicenseFederaleInput(absolutePath);
        await userDocumentsUpdatePage.setDocumentComplementaireInput(absolutePath);
        await userDocumentsUpdatePage.userSelectLastOption();
        await userDocumentsUpdatePage.save();
        expect(await userDocumentsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await userDocumentsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last UserDocuments', async () => {
        const nbButtonsBeforeDelete = await userDocumentsComponentsPage.countDeleteButtons();
        await userDocumentsComponentsPage.clickOnLastDeleteButton();

        userDocumentsDeleteDialog = new UserDocumentsDeleteDialog();
        expect(await userDocumentsDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this User Documents?');
        await userDocumentsDeleteDialog.clickOnConfirmButton();

        expect(await userDocumentsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
