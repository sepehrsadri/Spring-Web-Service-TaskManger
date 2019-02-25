package com.example.GroceryService.contoller;


import com.example.GroceryService.model.Grocery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;

@RestController
public class MyController {

    private ArrayList<Grocery> data = new ArrayList<>();

    public MyController() {
        Grocery item1 = new Grocery();
        Grocery item2 = new Grocery();
        item1.setId("000");
        item1.setName("First Apple");
        item1.setDate(Instant.now());
        item1.setDescription("first item that added!");
        item1.setNumber(2);
        item2.setId("111");
        item2.setName("First Banana");
        item2.setDate(Instant.now());
        item2.setDescription("second item that added!");
        item2.setNumber(3);
        this.data.add(item1);
        this.data.add(item2);
    }

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public String hello() {
        return "Hello World From Sepehr.com!";
    }

    @RequestMapping(value = "/goodbye", method = RequestMethod.POST)
    public ResponseEntity<String> goodbye(@RequestBody Grocery item) {
        int a = 10;
        int b = a * 5;
        return ResponseEntity.ok("Grocery item added successfully.");
    }

    @RequestMapping(value = "/grocery", method = RequestMethod.POST)
    public ResponseEntity<Grocery> add(@RequestBody Grocery item) {
        for(int l=0;l<data.size();l++){
            if(item.getName().toLowerCase().equals(data.get(l).getName().toLowerCase())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
        Integer id = (int) (Math.random() * 10000000.0);
//        System.out.println(id);
        item.setId(id.toString());
        item.setDate(Instant.now());

        data.add(0, item);
        test();
        return ResponseEntity.ok(item);
    }

    @RequestMapping(value = "/grocery", method = RequestMethod.GET)
    public ResponseEntity<Grocery[]> get() {
        return ResponseEntity.ok(data.toArray(new Grocery[data.size()]));
    }
    @RequestMapping(value = "grocery/{id}",method = RequestMethod.GET)
    public ResponseEntity<Grocery> get(@PathVariable String id){
        for (int i =0; i<data.size();i++){
            if (data.get(i).getId().equals(id)){
                return ResponseEntity.ok(data.get(i));
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/grocery/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Grocery> delete(@PathVariable String id) {
        for (int i = 0; i < data.size(); i++){
            if (data.get(i).getId().equals(id)) {
                data.remove(i);
                test();
                break;
            }
        }
        return ResponseEntity.ok(new Grocery());
    }
    @RequestMapping(value = "/grocery",method = RequestMethod.PUT)
    public ResponseEntity<Grocery> update(@RequestBody Grocery item) throws ParseException {
        for(int j=0;j<data.size();j++){
            if(data.get(j).getId().equals(item.getId())){
                data.get(j).setName(item.getName());
                data.get(j).setDescription(item.getDescription());
                data.get(j).setNumber(item.getNumber());
                data.get(j).setDate(Instant.now());
                test();
                return ResponseEntity.ok(data.get(j));

            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    public void test(){
        if(data.size()==0) {

            System.out.println("your data is empty!");
        }
        System.out.println("size of your data Array is :  " + data.size());
        for(int j=0;j<data.size();j++){
            System.out.println("now your data items & id are :  "+ data.get(j).getId() + "  "+ data.get(j).getName());
        }
    }
}
