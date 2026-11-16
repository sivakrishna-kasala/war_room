package com.krishna.statecollect_batch.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TestSiva {

    private final static Logger LOG= LogManager.getLogger(TestSiva.class);
    public void test() {

        Map<String,Integer> fruit=new HashMap<>();
        fruit.put("Apple",100);
        fruit.put("Orange",170);
        fruit.put("Mango",250);
        fruit.put("Watermelon",30);
        fruit.put("Banana",50);

        Map<String, List<String>> response=fruit.entrySet().stream()
                .collect(Collectors.groupingBy(entry->entry.getValue()>150?"Above 150":"Below 150",
                LinkedHashMap::new,
                Collectors.mapping(Map.Entry::getKey,Collectors.toList()
                )));

        System.out.println(response);

        Map<String, Integer> employees = new LinkedHashMap<>();

        employees.put("Ravi", 30000);
        employees.put("Krishna", 60000);
        employees.put("kk",10);
        employees.put("Ram", 45000);
        employees.put("Siva", 80000);
        employees.put("John", 25000);
        employees.put("VV",2000);
        employees.put("V",2000);


        LOG.info("{}",employees.entrySet().stream().
                max(Comparator.comparingInt(Map.Entry::getValue)).map(Map.Entry::getKey).stream().findFirst().get()
        );

        LOG.info("minimum : {}",employees.entrySet().
                stream().
                min(Comparator.comparingInt(Map.Entry::getValue)).get()
        );

        LOG.info("minimum : {}",employees.entrySet()
                .stream()
                .min(Comparator.comparingInt(Map.Entry::getValue)).map(Map.Entry::getKey).get()
        );

        Map<String,Integer>new1=employees.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey)).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(a,b)->a,LinkedHashMap::new));

        LOG.info("Sort map by keys asc : {}",new1
        );
        Map<String,Integer>new2=employees.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(a,b)->a,LinkedHashMap::new));

        LOG.info("Sort map by values : {}",new2);

        Map<String,Integer>new3=employees.entrySet().stream().sorted(Comparator.comparingInt((Map.Entry<String,Integer>entry)->entry.getValue()).reversed()).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(a,b)->b,LinkedHashMap::new));

        LOG.info("descending order of map : {}",new3);

        Set<Integer>dupl1=new HashSet<>();
        Map<String,Integer>dupl2=employees.entrySet().stream().filter(aa->!dupl1.add(aa.getValue())).collect(Collectors.toMap(
                Map.Entry::getKey,Map.Entry::getValue,(a,b)->a,LinkedHashMap::new
        ));

        LOG.info("Dupl : {}",dupl2);

//        employees.entrySet().stream().collect(Collectors.summarizingInt(Map.Entry::getValue)).getSum();
        LOG.info("sum :{}",employees.entrySet().stream().collect(Collectors.summarizingInt(Map.Entry::getValue)));
        LOG.info("Sum : {}",employees.entrySet().stream().collect(Collectors.summarizingInt(Map.Entry::getValue)).getSum());

        LOG.info("find avg : {}",employees.entrySet().stream().collect(Collectors.summarizingInt(Map.Entry::getValue)).getAverage());

        Map<String,Integer>greater=employees.entrySet().stream().filter(entry->entry.getValue()>2000).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(a,b)->a,LinkedHashMap::new));

        LOG.info("data : {}",greater);

        List<String> keys=employees.entrySet().stream().map(Map.Entry::getKey).toList();
        LOG.info("data : {}",keys);


        LOG.info("groupingBy : {}",employees.entrySet().stream().collect(Collectors.groupingBy(emp->emp.getValue()>5000?"Salary above: 5000":"Salary below: 5000")));

       Map<String,Integer>data2=employees.entrySet().stream().sorted(Comparator.comparingInt((Map.Entry<String,Integer>entry)->entry.getValue()).reversed()).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(a,b)->a,LinkedHashMap::new));

        LOG.info("Second highest salary: {}",data2);

        Map<String,Integer>sortedSalary=employees.entrySet()
                .stream()
                .sorted(Comparator.comparingInt((Map.Entry<String,Integer>entry)-> entry.getValue()).reversed()).skip(1).limit(1)
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(a,b)->a,LinkedHashMap::new));

        LOG.info("second highest salary : {}",sortedSalary);

        Map<String,Integer> sortedTop3=employees.entrySet().stream().sorted(Comparator.comparingInt((Map.Entry<String,Integer> entry)->entry.getValue()).reversed()).limit(3).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(a,b)->a,LinkedHashMap::new));

        LOG.info("top 3 : {}",sortedTop3);


                Double avg=employees.entrySet().stream()
                        .collect(Collectors.summarizingInt(entry-> entry.getValue())).getAverage();

        Map<String,Integer> greaterValues=employees.entrySet().stream().filter(entry->entry.getValue()>avg).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));

        LOG.info("keys greater than :{}",greaterValues);

        LOG.info("remove Null values : {}",employees.entrySet().stream().filter(entry->entry.getValue()!=null).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue)));

        employees.put("SS",null);
        employees.put("Sy",null);

        LOG.info("remove Null values : {}",employees);

        LOG.info("remove Null : {}",employees.entrySet().stream().filter(entry->entry.getValue()!=null).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue)));


        Map<String, Integer> map1 = new HashMap<>();

        map1.put("Apple", 100);
        map1.put("Orange", 200);
        map1.put("Mango", 300);

        Map<String, Integer> map2 = new HashMap<>();

        map2.put("Apple", 50);
        map2.put("Orange", 100);
        map2.put("Banana", 150);

        LOG.info("common keys : {}",map1.entrySet()
                .stream()
                .filter(entry->map2.containsKey(entry.getKey())).map(Map.Entry::getKey).toList()
        );


        Map<String,Integer> merge=map1.entrySet()
                .stream()
                .map(entry-> {

                    entry.setValue(entry.getValue() + map2.getOrDefault(entry.getKey(),0));

                    return entry;
                }
                ).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));


        LOG.info("merge two maps : {}",merge);

        LOG.info("Count : {}",employees.entrySet().stream().collect(Collectors.groupingBy(Map.Entry::getKey,Collectors.collectingAndThen(
                Collectors.counting(),count->count
        ))));

        LOG.info("Counting : {}",employees.entrySet().stream().filter(entry->entry.getValue()!=null)
                .collect(Collectors.groupingBy(Map.Entry::getValue,Collectors.collectingAndThen(Collectors.counting(),c->c))));

        class Employ{
            private Integer id;
            private String name;
            private String department;

            public Employ() {
            }

            public Employ(Integer id, String name, String department) {
                this.id = id;
                this.name = name;
                this.department = department;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDepartment() {
                return department;
            }

            public void setDepartment(String department) {
                this.department = department;
            }

            @Override
            public String toString() {
                return "Employ{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", department='" + department + '\'' +
                        '}';
            }
        }



        Map<Integer,Employ>emp=new HashMap<>();
        emp.put(1,new Employ(1,"R","Hr"));
        emp.put(2,new Employ(2,"S","School"));
        emp.put(3,new Employ(3,"K","Admin"));
        emp.put(4,new Employ(4,"Siva","Hr"));
        emp.put(5,new Employ(5,"Krishna","Admin"));
        emp.put(6,new Employ(6,"Kasala","IT"));

        LOG.info("group : {}",emp.entrySet().stream().collect(Collectors.groupingBy(entry->entry.getValue().getDepartment())));


         class Employee {

            private Integer empId;
            private String empName;
            private String department;
            private Double salary;

            public Employee(Integer empId, String empName,
                            String department, Double salary) {
                this.empId = empId;
                this.empName = empName;
                this.department = department;
                this.salary = salary;
            }

            public Integer getEmpId() {
                return empId;
            }

            public String getEmpName() {
                return empName;
            }

            public String getDepartment() {
                return department;
            }

            public Double getSalary() {
                return salary;
            }

            @Override
            public String toString() {
                return "Employee{" +
                        "empId=" + empId +
                        ", empName='" + empName + '\'' +
                        ", department='" + department + '\'' +
                        ", salary=" + salary +
                        '}';
            }
        }


        Map<Integer,Employee> employe = new HashMap<>();
        employe.put(1, new Employee(101, "Rahul", "IT", 60000.0));
         employe.put(2,       new Employee(102, "Krishna", "IT", 80000.0));
         employe.put(3,       new Employee(103, "Suresh", "IT", 90000.0));
         employe.put(4,       new Employee(104, "Pavan", "IT", 70000.0));
          employe.put(5,      new Employee(105, "Arjun", "HR", 45000.0));
          employe.put(6,      new Employee(106, "Kiran", "HR", 55000.0));
           employe.put(7,     new Employee(107, "Manoj", "HR", 65000.0));
           employe.put(8,     new Employee(108, "Ravi", "Finance", 75000.0));
           employe.put(9,     new Employee(109, "Sai", "Finance", 85000.0));
           employe.put(10,     new Employee(110, "Vijay", "Finance", 95000.0));
           employe.put(11,     new Employee(111, "Ajay", "Testing", 50000.0));
           employe.put(12,     new Employee(112, "Surya", "Testing", 60000.0));



         LOG.info("Highest salary : {}",employe.entrySet()
                 .stream()
                 .collect(Collectors
                         .groupingBy(entry->entry.getValue().getDepartment(),
                         Collectors.collectingAndThen(
                                 Collectors.maxBy(
                                         Comparator.comparingDouble(
                                                 entry->entry.getValue().getSalary())
                                 ),a->a
                         ))
                 )
         );

         LOG.info("Second Highest salary : {}",employe.entrySet()
                 .stream()
                 .collect(Collectors
                         .groupingBy(entry->entry.getValue().getDepartment(),
                                 Collectors.collectingAndThen(Collectors.toList(),entries -> entries.stream().sorted(Comparator.comparingDouble(ee->ee.getValue().getSalary())
                                         ).skip(1).limit(1).toList()
                                 )
                         )
                 )
         );


         LOG.info("average salary : {}",employe.entrySet()
                 .stream()
                 .collect(Collectors.groupingBy(entry->entry.getValue().getDepartment(),
                         Collectors.collectingAndThen(Collectors.averagingDouble(entry->entry.getValue().getSalary()),a->a))
                 )
         );

        List<Employee> duplicateEmployees = Arrays.asList(

                new Employee(101, "Rahul", "IT", 60000.0),
                new Employee(102, "Krishna", "IT", 80000.0),
                new Employee(103, "Rahul Updated", "IT", 70000.0),
                new Employee(104, "Suresh", "HR", 90000.0),
                new Employee(105, "Krishna Updated", "IT", 85000.0),
                new Employee(106, "Rahul", "IT", 60000.0),
                new Employee(107, "Krishna", "IT", 80000.0),
                new Employee(108, "Rahul siva", "IT", 70000.0),
                new Employee(109, "Suresh krishna", "HR", 90000.0),
                new Employee(110, "Krishn", "IT", 85000.0),
                new Employee(111, "Rahul", "IT", 60000.0),
                new Employee(112, "Krishna", "IT", 80000.0),
                new Employee(113, "Rahul Updated", "IT", 70000.0),
                new Employee(104, "Suresh", "HR", 90000.0),
                new Employee(105, "Krishna Updated", "IT", 85000.0),
                new Employee(106, "Rahul", "IT", 60000.0),
                new Employee(117, "Krishna", "IT", 80000.0),
                new Employee(108, "Rahul siva", "IT", 70000.0),
                new Employee(119, "Suresh krishna", "HR", 90000.0),
                new Employee(110, "Krishn", "IT", 85000.0)
        );

        Set<Integer>duplicat1=new HashSet<>();

        LOG.info("map : {}",duplicateEmployees.stream()
                .filter(em->em.getEmpId()!=null&&em.getEmpName()!=null&&em.getDepartment()!=null&&em.getSalary()!=null)
                .filter(ee->!duplicat1.add(ee.getEmpId()))
                .collect(Collectors.toMap(Employee::getEmpId,employee -> employee)));

        Set<Integer>duplicate=new HashSet<>();

        Map<Integer,Employee>result=duplicateEmployees.stream()
                .filter(ee->!duplicate.add(ee.getEmpId()))
                .collect(Collectors.toMap(Employee::getEmpId,employee -> employee));


        LOG.info("data : {}",result.entrySet().stream()
                .collect(Collectors.
                        groupingBy((Map.Entry<Integer,Employee>entry)->entry.getValue().getDepartment(),
                               Collectors.averagingDouble(eee->eee.getValue().getSalary()
                                )
                        )
                )
        );

        LOG.info("average salary : {}",employe.entrySet()
                .stream()
                .collect(Collectors.groupingBy(entry->entry.getValue().getDepartment(),
                        Collectors.collectingAndThen(Collectors.averagingDouble(entry->entry.getValue().getSalary()),a->a))
                )
        );

        LOG.info(" highest average salary : {}",employe.entrySet()
                .stream()
                .collect(Collectors.groupingBy(entry->entry.getValue().getDepartment(),
                        Collectors.collectingAndThen(Collectors.averagingDouble(entry->entry.getValue().getSalary()),a->a))
                ).entrySet().stream().max(Map.Entry.comparingByValue()).get()
        );

        List<String> words = Arrays.asList(
                "Java",
                "Spring",
                "SQL",
                "Hibernate",
                "API",
                "Python",
                "Go",
                "Microservices",
                "Docker",
                "Boot",
                "C",
                "React",
                "JavaScript",
                "AWS",
                "SpringBoot"
        );

        LOG.info("grouping by : {}",words.stream().collect(Collectors.groupingBy(aa->aa.length())));

        LOG.info("group : {}",words.stream().collect(Collectors.groupingBy(ss-> {

                    return ss.substring(0,1);

        })));


        String string="Big black bug bit a big black dog on his big black nose";

        LOG.info("word count : {}", Arrays.stream(string.split(" ")).collect(Collectors.groupingBy(aa->aa,Collectors.counting())));

        Set<String> dup=new HashSet<>();

        LOG.info("words : {}",Arrays.stream(string.split(" ")).filter(aa->!dup.add(aa)).toList());


    }
}
