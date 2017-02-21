package com.metabolic_app.server.servlets;

import com.metabolic_app.data.EntryType;
import com.metabolic_app.data.InboxEntry;
import com.metabolic_app.data.accessObject.InboxAccessObject;

import javax.naming.InvalidNameException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Code based off: http://stackoverflow.com/questions/2340406/how-to-retrieve-and-display-images-from-a-database-in-a-jsp-page
 */
public class ImageSourceServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(String.valueOf(this.getClass()));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long entry_id = Long.valueOf(req.getParameter("entry_id"));
        InboxEntry inboxEntry;
        try {
            inboxEntry = new InboxAccessObject().getRequest(entry_id);
            if(inboxEntry.getEntryType() == EntryType.REQUEST) return;
            Blob image = inboxEntry.getImage();
            resp.setContentType("image/jpeg");
            resp.setContentLength((int) image.length());
            resp.getOutputStream().write(image.getBytes(0, (int) image.length()));
            return;
        } catch (InvalidNameException |SQLException e) {
            logger.warning("Error finding image for request " + e);
            return;
        }
    }
}
