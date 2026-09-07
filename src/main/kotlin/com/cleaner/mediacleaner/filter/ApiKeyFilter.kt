package com.cleaner.mediacleaner.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class ApiKeyFilter(@Value("\${api.key}") private val apiKey: String) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestKey = request.getHeader("X-Api-Key")
        if (requestKey == null || requestKey != apiKey) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API key")
            return
        }
        filterChain.doFilter(request, response)
    }
}