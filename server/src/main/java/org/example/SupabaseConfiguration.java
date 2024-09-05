package org.example;

import io.github.jan.supabase.SupabaseClient;
import io.github.jan.supabase.postgrest.Postgrest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SupabaseConfiguration {
    private final String supabaseUrl = null;
    private final String publicAnonKey = null;

    @Bean
    public SupabaseClient supabaseClient() {
        return SupabaseClientProvider.createSupabaseClient(
                supabaseUrl,
                publicAnonKey
        );
    }
}
