/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InscriptionComponentsPage, InscriptionDeleteDialog, InscriptionUpdatePage } from './inscription.page-object';

const expect = chai.expect;

describe('Inscription e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let inscriptionUpdatePage: InscriptionUpdatePage;
    let inscriptionComponentsPage: InscriptionComponentsPage;
    let inscriptionDeleteDialog: InscriptionDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Inscriptions', async () => {
        await navBarPage.goToEntity('inscription');
        inscriptionComponentsPage = new InscriptionComponentsPage();
        expect(await inscriptionComponentsPage.getTitle()).to.eq('Inscriptions');
    });

    it('should load create Inscription page', async () => {
        await inscriptionComponentsPage.clickOnCreateButton();
        inscriptionUpdatePage = new InscriptionUpdatePage();
        expect(await inscriptionUpdatePage.getPageTitle()).to.eq('Create or edit a Inscription');
        await inscriptionUpdatePage.cancel();
    });

    it('should create and save Inscriptions', async () => {
        const nbButtonsBeforeCreate = await inscriptionComponentsPage.countDeleteButtons();

        await inscriptionComponentsPage.clickOnCreateButton();
        await inscriptionUpdatePage.roleSelectLastOption();
        await inscriptionUpdatePage.setUserInput('5');
        expect(await inscriptionUpdatePage.getUserInput()).to.eq('5');
        await inscriptionUpdatePage.setCourseInput('5');
        expect(await inscriptionUpdatePage.getCourseInput()).to.eq('5');
        await inscriptionUpdatePage.save();
        expect(await inscriptionUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await inscriptionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Inscription', async () => {
        const nbButtonsBeforeDelete = await inscriptionComponentsPage.countDeleteButtons();
        await inscriptionComponentsPage.clickOnLastDeleteButton();

        inscriptionDeleteDialog = new InscriptionDeleteDialog();
        expect(await inscriptionDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Inscription?');
        await inscriptionDeleteDialog.clickOnConfirmButton();

        expect(await inscriptionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
