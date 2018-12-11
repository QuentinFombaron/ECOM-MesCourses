import { element, by, ElementFinder } from 'protractor';

export class UserDocumentsComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-user-documents div table .btn-danger'));
    title = element.all(by.css('jhi-user-documents div h2#page-heading span')).first();

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

export class UserDocumentsUpdatePage {
    pageTitle = element(by.id('jhi-user-documents-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dateDeNaissanceInput = element(by.id('field_dateDeNaissance'));
    sexeSelect = element(by.id('field_sexe'));
    certificatMedicalInput = element(by.id('file_certificatMedical'));
    licenseFederaleInput = element(by.id('file_licenseFederale'));
    documentComplementaireInput = element(by.id('file_documentComplementaire'));
    userSelect = element(by.id('field_user'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setDateDeNaissanceInput(dateDeNaissance) {
        await this.dateDeNaissanceInput.sendKeys(dateDeNaissance);
    }

    async getDateDeNaissanceInput() {
        return this.dateDeNaissanceInput.getAttribute('value');
    }

    async setSexeSelect(sexe) {
        await this.sexeSelect.sendKeys(sexe);
    }

    async getSexeSelect() {
        return this.sexeSelect.element(by.css('option:checked')).getText();
    }

    async sexeSelectLastOption() {
        await this.sexeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setCertificatMedicalInput(certificatMedical) {
        await this.certificatMedicalInput.sendKeys(certificatMedical);
    }

    async getCertificatMedicalInput() {
        return this.certificatMedicalInput.getAttribute('value');
    }

    async setLicenseFederaleInput(licenseFederale) {
        await this.licenseFederaleInput.sendKeys(licenseFederale);
    }

    async getLicenseFederaleInput() {
        return this.licenseFederaleInput.getAttribute('value');
    }

    async setDocumentComplementaireInput(documentComplementaire) {
        await this.documentComplementaireInput.sendKeys(documentComplementaire);
    }

    async getDocumentComplementaireInput() {
        return this.documentComplementaireInput.getAttribute('value');
    }

    async userSelectLastOption() {
        await this.userSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async userSelectOption(option) {
        await this.userSelect.sendKeys(option);
    }

    getUserSelect(): ElementFinder {
        return this.userSelect;
    }

    async getUserSelectedOption() {
        return this.userSelect.element(by.css('option:checked')).getText();
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

export class UserDocumentsDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-userDocuments-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-userDocuments'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
