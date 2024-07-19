//package com.room.booking.service;
//
//import com.room.booking.dao.RoomDaoImpl;
//import com.room.booking.model.Room;
//import com.room.booking.model.RoomDTO;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class RoomServiceImpl {
//
//    RoomDaoImpl roomDao;
//
//
//    public RoomServiceImpl() {
//
//        this.roomDao = new RoomDaoImpl();
//    }
//
//    //get all users
//
//   public List<RoomDTO> fetchAllRooms() {
//        List<RoomDTO> roomDTOS = new ArrayList<>();
//        final List<Room> allRooms = roomDao.getAllRooms();
//
//        for (Room r :allRooms) {
//            RoomDTO roomDTO = new RoomDTO(r.getRoomName(), r.getCapacity(), r.getLocation(), r.getFeatures());
//            roomDTOS.add(roomDTO);
//        }
//
//       return roomDTOS;
//   }
//
//
//    public Room getRoomByName(String selectedRoom) {
//
//        return roomDao.getRoomByName(selectedRoom);
//    }
//}
