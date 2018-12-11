import { element, by, ElementFinder } from 'protractor';

export class CourseComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-course div table .btn-danger'));
    title = element.all(by.css('jhi-course div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class CourseUpdatePage {
    pageTitle = element(by.id('jhi-course-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    organisateurInput = element(by.id('field_organisateur'));
    titreInput = element(by.id('field_titre'));
    descriptionInput = element(by.id('field_description'));
    sportSelect = element(by.id('field_sport'));
    dateInput = element(by.id('field_date'));
    heureInput = element(by.id('field_heure'));
    longitudeInput = element(by.id('field_longitude'));
    latitudeInput = element(by.id('field_latitude'));
    lieuInput = element(by.id('field_lieu'));
    prixInput = element(by.id('field_prix'));
    image1Input = element(by.id('file_image1'));
    image2Input = element(by.id('file_image2'));
    image3Input = element(by.id('file_image3'));
    image4Input = element(by.id('file_image4'));
    image5Input = element(by.id('file_image5'));
    participantsSelect = element(by.id('field_participants'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setOrganisateurInput(organisateur) {
        await this.organisateurInput.sendKeys(organisateur);
    }

    async getOrganisateurInput() {
        return this.organisateurInput.getAttribute('value');
    }

    async setTitreInput(titre) {
        await this.titreInput.sendKeys(titre);
    }

    async getTitreInput() {
        return this.titreInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async setSportSelect(sport) {
        await this.sportSelect.sendKeys(sport);
    }

    async getSportSelect() {
        return this.sportSelect.element(by.css('option:checked')).getText();
    }

    async sportSelectLastOption() {
        await this.sportSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setDateInput(date) {
        await this.dateInput.sendKeys(date);
    }

    async getDateInput() {
        return this.dateInput.getAttribute('value');
    }

    async setHeureInput(heure) {
        await this.heureInput.sendKeys(heure);
    }

    async getHeureInput() {
        return this.heureInput.getAttribute('value');
    }

    async setLongitudeInput(longitude) {
        await this.longitudeInput.sendKeys(longitude);
    }

    async getLongitudeInput() {
        return this.longitudeInput.getAttribute('value');
    }

    async setLatitudeInput(latitude) {
        await this.latitudeInput.sendKeys(latitude);
    }

    async getLatitudeInput() {
        return this.latitudeInput.getAttribute('value');
    }

    async setLieuInput(lieu) {
        await this.lieuInput.sendKeys(lieu);
    }

    async getLieuInput() {
        return this.lieuInput.getAttribute('value');
    }

    async setPrixInput(prix) {
        await this.prixInput.sendKeys(prix);
    }

    async getPrixInput() {
        return this.prixInput.getAttribute('value');
    }

    async setImage1Input(image1) {
        await this.image1Input.sendKeys(image1);
    }

    async getImage1Input() {
        return this.image1Input.getAttribute('value');
    }

    async setImage2Input(image2) {
        await this.image2Input.sendKeys(image2);
    }

    async getImage2Input() {
        return this.image2Input.getAttribute('value');
    }

    async setImage3Input(image3) {
        await this.image3Input.sendKeys(image3);
    }

    async getImage3Input() {
        return this.image3Input.getAttribute('value');
    }

    async setImage4Input(image4) {
        await this.image4Input.sendKeys(image4);
    }

    async getImage4Input() {
        return this.image4Input.getAttribute('value');
    }

    async setImage5Input(image5) {
        await this.image5Input.sendKeys(image5);
    }

    async getImage5Input() {
        return this.image5Input.getAttribute('value');
    }

    async participantsSelectLastOption() {
        await this.participantsSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async participantsSelectOption(option) {
        await this.participantsSelect.sendKeys(option);
    }

    getParticipantsSelect(): ElementFinder {
        return this.participantsSelect;
    }

    async getParticipantsSelectedOption() {
        return this.participantsSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class CourseDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-course-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-course'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
