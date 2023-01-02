package com.iw.application.security;


// TODO neccessary?
//@Component
//public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {
//
//	@Override
//	public void serviceInit(ServiceInitEvent event) {
//		event.getSource().addUIInitListener(uiEvent -> {
//			final UI ui = uiEvent.getUI();
//			ui.addBeforeEnterListener(this::authenticateNavigation);
//		});
//	}
//
//	private void authenticateNavigation(BeforeEnterEvent event) {
//		if (!LoginView.class.equals(event.getNavigationTarget())
//		    && !SecurityUtils.isUserLoggedIn()) {
//			event.rerouteTo(LoginView.class);
//		}
//	}
//}