package com.game.java.web;

import com.game.java.model.jdbc.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static java.util.Objects.isNull;

public class RoomPageServlet extends HttpServlet {
    private RoomDao roomDao;
    private UserDao userDao;

    @Override
    public void init() {
        this.roomDao = RoomDaoImpl.getInstance();
        this.userDao = UserDaoImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (isNull(req.getSession().getAttribute("user"))) {
            resp.sendRedirect(req.getContextPath() + "/homePage");
        } else {
            Optional<Room> room = roomDao.getRoomById(getRoomIdFromPath(req));
            if (room.isPresent()) {
                req.setAttribute("room", room.get());
                req.setAttribute("users", roomDao.getUsersFromRoom(room.get()));
                req.getRequestDispatcher("/WEB-INF/pages/roomDetails.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<Room> room = roomDao.getRoomById(getRoomIdFromPath(req));
        if (room.isPresent()) {
            req.setAttribute("room", room.get());
            User user = (User) req.getSession().getAttribute("user");
            roomDao.addUserToRoom(user, room.get());
            req.getSession().setAttribute("user", userDao.getUserById(user.getId()).get());
            resp.sendRedirect(req.getContextPath() + "/room/" + room.get().getId());
        } else {
            resp.setStatus(404);
            req.getRequestDispatcher("/WEB-INF/pages/roomDetails.jsp").forward(req, resp);
        }
    }

    protected int getRoomIdFromPath(HttpServletRequest request) {
        return Integer.valueOf(request.getPathInfo().substring(1));
    }
}
