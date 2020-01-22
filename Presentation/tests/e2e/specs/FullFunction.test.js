// For authoring Nightwatch tests, see
// https://nightwatchjs.org/guide

module.exports = {
    'new group e2e test': browser => {
        browser
            .init()
            .waitForElementVisible('#app')
            .assert.elementPresent('input[id=groupname]')
            .assert.elementPresent('input[id=name]')
            .assert.elementPresent('input[id=key]')
            .assert.elementPresent('button[id=next]')
            .assert.elementPresent('button[id=routecreategroup]')
            .assert.elementPresent('nav[id=navigation]')
            .assert.elementPresent('header[id=header]')
            .end()
    },
};

