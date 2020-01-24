// For authoring Nightwatch tests, see
// https://nightwatchjs.org/guide

module.exports = {
    'startup new group e2e test': browser => {
        browser.init(browser.launchUrl+'newgroup/')
            .waitForElementVisible('#app')
            .assert.elementPresent('input[id=groupname]')
            .assert.visible('input[id=groupname]')
            .assert.elementPresent('input[id=name]')
            .assert.visible('input[id=name]')
            .assert.elementPresent('input[id=enableRerolls]')
            .assert.elementPresent('input[id=rerolls]')
            .assert.visible('input[id=rerolls]')
            .assert.attributeContains('input[id=rerolls]', 'disabled', 'true')
            .click('label[for="enableRerolls"]')
            .assert.not.attributeContains('input[id=rerolls]', 'disabled', 'true')
            .click('label[for="enableRerolls"]')
            .assert.attributeContains('input[id=rerolls]', 'disabled', 'true')
            .assert.elementPresent('button[id=back]')
            .assert.visible('button[id=back]')
            .assert.elementPresent('button[id=next]')
            .assert.visible('button[id=next]')
            .assert.elementPresent('nav[id=navigation]')
            .assert.visible('nav[id=navigation]')
            .assert.elementPresent('header[id=header]')
            .assert.visible('header[id=header]')
            .end()
    },
    'no credentials group e2e test': browser => {
        browser.init()
            .waitForElementVisible('#app')
            .assert.elementPresent('button[id=next]')
            .assert.visible('button[id=next]')
            .assert.not.elementPresent('div[id="erroralert"]')
            .click('button[id=next]')
            .waitForElementPresent('div[id="erroralert"]')
            .assert.elementPresent('div[id="erroralert"]')
            .assert.visible('div[id="erroralert"]')
            .end()
    },
    'navigation drawer new group e2e test': browser => {
        browser
            .init(browser.launchUrl+'newgroup/')
            .waitForElementVisible('#app')
            .assert.elementPresent('nav[id=navigation]')
            .assert.visible('nav[id=navigation]')
            .assert.cssClassPresent('nav[id=navigation]', 'v-navigation-drawer--close')
            .click('button[id="changenav"]')
            .assert.elementPresent('nav[id=navigation]')
            .assert.visible('nav[id=navigation]')
            .assert.cssClassPresent('nav[id=navigation]', 'v-navigation-drawer--open')
    },
};

