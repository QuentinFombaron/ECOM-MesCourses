/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CourseComponentsPage, CourseDeleteDialog, CourseUpdatePage } from './course.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Course e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let courseUpdatePage: CourseUpdatePage;
    let courseComponentsPage: CourseComponentsPage;
    let courseDeleteDialog: CourseDeleteDialog;
    const fileToUpload = '../../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Courses', async () => {
        await navBarPage.goToEntity('course');
        courseComponentsPage = new CourseComponentsPage();
        expect(await courseComponentsPage.getTitle()).to.eq('Courses');
    });

    it('should load create Course page', async () => {
        await courseComponentsPage.clickOnCreateButton();
        courseUpdatePage = new CourseUpdatePage();
        expect(await courseUpdatePage.getPageTitle()).to.eq('Create or edit a Course');
        await courseUpdatePage.cancel();
    });

    it('should create and save Courses', async () => {
        const nbButtonsBeforeCreate = await courseComponentsPage.countDeleteButtons();

        await courseComponentsPage.clickOnCreateButton();
        await courseUpdatePage.setOrganisateurInput('5');
        expect(await courseUpdatePage.getOrganisateurInput()).to.eq('5');
        await courseUpdatePage.setTitreInput('titre');
        expect(await courseUpdatePage.getTitreInput()).to.eq('titre');
        await courseUpdatePage.setDescriptionInput('description');
        expect(await courseUpdatePage.getDescriptionInput()).to.eq('description');
        await courseUpdatePage.sportSelectLastOption();
        await courseUpdatePage.setDateInput('2000-12-31');
        expect(await courseUpdatePage.getDateInput()).to.eq('2000-12-31');
        await courseUpdatePage.setHeureInput('heure');
        expect(await courseUpdatePage.getHeureInput()).to.eq('heure');
        await courseUpdatePage.setLongitudeInput('5');
        expect(await courseUpdatePage.getLongitudeInput()).to.eq('5');
        await courseUpdatePage.setLatitudeInput('5');
        expect(await courseUpdatePage.getLatitudeInput()).to.eq('5');
        await courseUpdatePage.setLieuInput('lieu');
        expect(await courseUpdatePage.getLieuInput()).to.eq('lieu');
        await courseUpdatePage.setPrixInput('5');
        expect(await courseUpdatePage.getPrixInput()).to.eq('5');
        await courseUpdatePage.setImage1Input(absolutePath);
        await courseUpdatePage.setImage2Input(absolutePath);
        await courseUpdatePage.setImage3Input(absolutePath);
        await courseUpdatePage.setImage4Input(absolutePath);
        await courseUpdatePage.setImage5Input(absolutePath);
        // courseUpdatePage.participantsSelectLastOption();
        await courseUpdatePage.save();
        expect(await courseUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await courseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Course', async () => {
        const nbButtonsBeforeDelete = await courseComponentsPage.countDeleteButtons();
        await courseComponentsPage.clickOnLastDeleteButton();

        courseDeleteDialog = new CourseDeleteDialog();
        expect(await courseDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Course?');
        await courseDeleteDialog.clickOnConfirmButton();

        expect(await courseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
