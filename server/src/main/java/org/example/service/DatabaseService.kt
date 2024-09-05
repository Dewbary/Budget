package org.example.service

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.runBlocking
import org.example.model.PlaidTokenDto
import org.example.model.ResultDto
import org.springframework.stereotype.Service
@Service
class DatabaseService(supabase: SupabaseClient) {
    val postgrest: Postgrest = supabase.postgrest;

    fun getStuff(): List<ResultDto> {
        val result = runBlocking {
            postgrest.from("brendan_test").select(Columns.ALL).decodeList<ResultDto>()
        }
        return result
    }

    fun savePlaidTokens(accessToken: String, itemId: String) {
        runBlocking {
            postgrest.from("plaid_tokens").insert(
                PlaidTokenDto(
                    accessToken,
                    itemId
                )
            )
        }
    }

}
