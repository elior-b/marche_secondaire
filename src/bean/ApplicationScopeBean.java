package bean;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@ApplicationScoped
public class ApplicationScopeBean {

	public void preRenderView() {
		try {
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// tune session params, eg. session.setMaxInactiveInterval(..);

		// perform other pre-render stuff, like setting user context...
	}

}