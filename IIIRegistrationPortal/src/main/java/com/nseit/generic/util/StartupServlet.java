package com.nseit.generic.util;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StartupServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(StartupServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        logger.info("StartupServlet initialized. Loading common data...");

        // TODO: Implement logic to load common data (e.g., countries, static lists) into application scope
        // Example: config.getServletContext().setAttribute("countries", new CountryService().getAllCountries());
        // This will require Spring context to be available, or direct DAO calls if Spring is not yet fully initialized.
        // For now, this is a placeholder.
    }

    @Override
    public void destroy() {
        logger.info("StartupServlet destroyed.");
        super.destroy();
    }
}
