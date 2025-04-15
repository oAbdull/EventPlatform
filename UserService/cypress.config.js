const { defineConfig } = require('cypress');
module.exports = defineConfig({ e2e: { baseUrl: 'http://localhost:8091', specPattern: 'e2e/**/*.cy.js', supportFile: 'cypress/support/e2e.js', setupNodeEvents(on, config) {}, video: true, screenshotOnRunFailure: true, }, });
