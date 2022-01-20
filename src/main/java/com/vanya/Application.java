package com.vanya;

import com.vanya.entities.*;
import com.vanya.requests.AddressRequests;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.cli.*;
import com.vanya.requests.ChildRequests;
import com.vanya.requests.SchoolRequests;
import com.vanya.requests.ParentsRequests;

public class Application {
    public static void main(String[] args) {
        if (new AddressRequests().getAddresss().size() == 0) {
            System.out.println("parse data...");
            parseDistricts();
        }

        Options commands = new Options();
        Option fatherName = new Option("f", "father", true, "set father name");
        commands.addOption(fatherName);
        Option addParents = new Option("p", "addParents", false, "add parents");
        commands.addOption(addParents);
        Option motherName = new Option("m", "mother", true, "set mother name");
        commands.addOption(motherName);
        Option setSchool = new Option("s", "setSchool", true, "set school");
        setSchool.setArgs(2);
        commands.addOption(setSchool);
        Option setAddress = new Option("a", "setAddress", true, "set local address");
        setAddress.setArgs(2);
        commands.addOption(setAddress);
        Option addChild = new Option("c", "addChild", true, "add child");
        addChild.setArgs(3);
        commands.addOption(addChild);

        CommandLineParser parser = new org.apache.commons.cli.BasicParser();
        HelpFormatter helpFormatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(commands, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helpFormatter.printHelp("utility-name", commands);
            System.exit(1);
        }

        if (cmd.hasOption('a')) {ChangeAddress(cmd.getOptionValues('a'));}
        if (cmd.hasOption('p')) {AddParents(cmd);}
        if (cmd.hasOption('s')) {ChangeSchool(cmd.getOptionValues('e'));}
        if (cmd.hasOption('c')) {AddChild(cmd.getOptionValues('c'));}
    }

    private static void ChangeSchool(String[] args) {
        int childId = Integer.parseInt(args[0]);
        int schoolId = Integer.parseInt(args[1]);
        ChildRequests childRequests = new ChildRequests();
        Child child = childRequests.getById(childId);
        child.school = new SchoolRequests().getById(schoolId);
        child.update_child();
    }

    private static void ChangeAddress(String[] args) {
        int parentsId = Integer.parseInt(args[0]);
        int addressId = Integer.parseInt(args[1]);
        ParentsRequests parentsRequests = new ParentsRequests();
        Parents parents = parentsRequests.getById(parentsId);
        parents.address = new AddressRequests().getById(addressId);
        parents.update_parents();
    }

    private static void AddChild(String[] args) {
        String fullname = args[0];
        int parentsId = Integer.parseInt(args[1]);
        int age = Integer.parseInt(args[2]);
        Parents parents = new ParentsRequests().getById(parentsId);

        if (parents.address != null) {
            List<School> schools = new SchoolRequests().getSchools();
            List<School> anotherschools =
                    schools.stream().filter(x -> parents.address.district.addresses.stream().anyMatch
                            (y -> y.id == x.address.id)).collect(Collectors.toList());
            anotherschools.forEach(x -> System.out.println("Appropriated school ! Name " + x.number + ". Id: " + x.id));
        }
        Child child = new Child(fullname, parents, age);
        child.save_child();
        System.out.println("Child added. Id " + child.id);
    }

    private static void AddParents(CommandLine args) {
        Parents parents = new Parents();
        if (args.hasOption('m')) {
            String motherFullname = args.getOptionValue('m');
            parents.motherFullname = motherFullname;
        }
        if (args.hasOption('f')) {
            String fatherFullname = args.getOptionValue('f');
            parents.fatherFullname = fatherFullname;
        }
        parents.save_parents();
        System.out.println("Parent(s) added. Id " + " "  + parents.id + " " + "Father_name-" + " " + parents.fatherFullname + " " + "Mother_name-" + " " + parents.motherFullname );
    }

    public static void parseDistricts() {
        District[] districts = new District[]{new District(), new District(), new District(),};
        for (District district : districts) {
            district.save_district();
        }
        int i = 0;

        List<Address> addresses = new ArrayList<>(25);
        for (String addressName : new String[]{
                "93403 Fahey Harbors Port Jermain",
                "57962 Pagac Meadow Shannonton",
                "464 Gerardo Radial Raymouth",
                "60001 Clare Parkway Apt. 574 Sallyville",
                "709 Davis Valleys Apt. 510 East Samirton",
                "703 Price Grove Apt. 770 Jacobschester",
                "1751 DuBuque Harbor Apt. 497 Lake Linnea",
                "1097 Kling Points Suite 513 West Curtmouth",
                "260 Satterfield Lodge North Marcellusmouth",
                "8908 Glennie Pine East Joaquin",
                "510 Hyatt Dam Turcotteborough",
        }) {
            Address address = new Address(addressName);
            addresses.add(address);
            address.district = districts[i % 3];
            address.saveAddress();
            i++;
        }
        for (i = 0; i < 10; i++) {
            School school = new School("School №" + i, addresses.get(i));
            school.save_school();
        }
    }
}
