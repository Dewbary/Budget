package org.example

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.serializer.JacksonSerializer

object SupabaseClientProvider {
    @JvmStatic
    fun createSupabaseClient(supabaseUrl: String, supabaseKey: String): SupabaseClient {
        val supabase = createSupabaseClient(supabaseUrl, supabaseKey) {
            install(Postgrest)
            defaultSerializer = JacksonSerializer()
        }
        return supabase;
    }
}
