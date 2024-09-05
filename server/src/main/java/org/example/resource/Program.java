package org.example.resource;

import io.github.jan.supabase.SupabaseClient;
import io.github.jan.supabase.plugins.SupabasePlugin;
import io.github.jan.supabase.postgrest.Postgrest;
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder;
import org.example.SupabaseClientProvider;
import org.example.SupabaseConfiguration;
import org.example.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
@Component
public class Program {

    @Configuration
    @ComponentScan(basePackageClasses = Company.class)
    public static class Config {
        @Bean
        public Address getAddress() {
            return new Address("Main St", 123);
        }
    }

    @Component
    record Company(Address address) {}

    record Address(String street, int number) {}


    private SupabaseClient supabaseClient;
    private DatabaseService databaseService;

    @Autowired
    public Program(SupabaseClient supabaseClient, DatabaseService databaseService) {
        this.supabaseClient = supabaseClient;
        this.databaseService = databaseService;
    }

    public void test() {
//        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
//        Company company = context.getBean(Company.class);
//        System.out.println(company.address.street);
//        SupabaseClient supabase = new SupabaseConfiguration().supabaseClient();
//        Postgrest postgrest = (Postgrest) supabaseClient.getPluginManager().getInstalledPlugins().get("rest");
//        PostgrestQueryBuilder builder = postgrest.from("brendan_test");
        databaseService.getStuff();
        System.out.println("here");

//        String table = postgrest.from("brendan_test").getTable();
//        System.out.println(table);
    }

//    public static void main(String[] args) {
//        new Program().test();
//    }
};
