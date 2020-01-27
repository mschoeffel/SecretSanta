// For authoring Nightwatch tests, see
// https://nightwatchjs.org/guide

module.exports = {
    'startup show group e2e test': browser => {
        browser.init(browser.launchUrl+'group/')
            .waitForElementVisible('#app')
            .assert.elementPresent('input[id=groupname]')
            .assert.visible('input[id=groupname]')
            .assert.elementPresent('input[id=grouptoken]')
            .assert.visible('input[id=grouptoken]')
            .assert.elementPresent('button[id=back]')
            .assert.visible('button[id=back]')
            .assert.elementPresent('button[id=next]')
            .assert.visible('button[id=next]')
            .assert.elementPresent('nav[id=navigation]')
            .assert.visible('nav[id=navigation]')
            .assert.elementPresent('header[id=header]')
            .assert.visible('header[id=header]')
            .end()
    }
};

