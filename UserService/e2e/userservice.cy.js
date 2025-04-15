describe('UserService E2E Tests', () => { it('should check if the userservice is responding', () => { cy.request('/actuator/health').its('status').should('eq', 200); }); });
