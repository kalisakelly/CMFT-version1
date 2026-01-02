package com.example.demo.servlet;

import java.io.IOException;
import java.util.Map;

import com.example.demo.service.CarService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FuelStatsServlet extends HttpServlet {

    private CarService service;

    @Override
    public void init() {
        service = (CarService) getServletContext()
                .getAttribute("carService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        String carIdParam = req.getParameter("carId");
        if (carIdParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"carId required\"}");
            return;
        }

        int carId = Integer.parseInt(carIdParam);
        Map<String, Double> stats = service.getFuelStats(carId);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(stats.toString());
    }
}
