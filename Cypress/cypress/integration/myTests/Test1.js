describe('Automation Panda Test Suite', () => {
    let testData;
  
    // Load fixture data before tests
    before(() => {
      cy.fixture('testData').then((data) => {
        testData = data;
      });
    });
  
    // Test case: Launch URL and verify the title
    it('Launch URL and verify the title', () => {
      cy.visit(testData.url);
      cy.title().should('eq', testData.mainTitle);

    //Click on Speaking and verify the title
      cy.contains('Speaking').click();
      cy.title().should('eq', testData.speakingPageTitle);

    //Verify Keynote Addresses presence and text
      cy.contains(testData.keynoteText).should('be.visible');
      cy.contains(testData.keynoteText).should('have.text', testData.keynoteText);
    });
    
  });