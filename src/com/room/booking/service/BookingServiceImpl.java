package com.room.booking.service;

import com.room.booking.dao.BookingDaoImpl;
import com.room.booking.util.DateUtil;

import java.time.LocalDateTime;
import java.util.Date;

public class BookingServiceImpl {


    BookingDaoImpl bookingDao;

    public BookingServiceImpl() {
        this.bookingDao = new BookingDaoImpl();
    }

    public void createBooking(int roomId, int userId, String selectedRoom, String bookerName, Date startDate, Date endDate, String purpose) {

        String status = "IN_PROGRESS";

        final LocalDateTime localDateTimeStart = DateUtil.convertDateToLocalDateTime(startDate);
        final LocalDateTime localDateTimeEnd = DateUtil.convertDateToLocalDateTime(endDate);

        bookingDao.createBooking(roomId, userId, localDateTimeStart, localDateTimeEnd, purpose, status);

    }


}
