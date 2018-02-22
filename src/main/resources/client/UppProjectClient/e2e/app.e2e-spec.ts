import { UppProjectClientPage } from './app.po';

describe('upp-project-client App', function() {
  let page: UppProjectClientPage;

  beforeEach(() => {
    page = new UppProjectClientPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
