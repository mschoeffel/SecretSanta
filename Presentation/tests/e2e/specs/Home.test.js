// For authoring Nightwatch tests, see
// https://nightwatchjs.org/guide

module.exports = {
    'startup home e2e test': browser => {
        browser
            .init()
            .waitForElementVisible('#app')
            .assert.elementPresent('input[id=groupname]')
            .assert.visible('input[id=groupname]')
            .assert.elementPresent('input[id=name]')
            .assert.visible('input[id=name]')
            .assert.elementPresent('input[id=key]')
            .assert.visible('input[id=key]')
            .assert.elementPresent('button[id=next]')
            .assert.visible('button[id=next]')
            .assert.elementPresent('button[id=routecreategroup]')
            .assert.visible('button[id=routecreategroup]')
            .assert.elementPresent('nav[id=navigation]')
            .assert.visible('nav[id=navigation]')
            .assert.elementPresent('header[id=header]')
            .assert.visible('header[id=header]')
    },
    'no credentials draw e2e test': browser =>{
        browser
        .init()
        .waitForElementVisible('#app')
        .assert.not.elementPresent('div[id="erroralert"]')
        .assert.elementPresent('button[id=next]')
        .assert.visible('button[id=next]')
        .click('button[id=next]')
        .pause(100)
        .assert.elementPresent('div[id="erroralert"]')
        .assert.visible('div[id="erroralert"]')
        .end()
    },
    'navigation drawer home e2e test': browser => {
        browser
            .init()
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

