package com.domain.wiseSaying.wiseSaying.repository

import com.domain.wiseSaying.wiseSaying.entity.WiseSaying
import com.standard.dto.Page
import kotlin.collections.drop
import kotlin.collections.filter

class WiseSayingMemoryRepository : WiseSayingRepository {
    private var lastId = 0
    private val wiseSayings = mutableListOf<WiseSaying>()

    override fun save(wiseSaying: WiseSaying): WiseSaying {
        if (wiseSaying.isNew()) {
            wiseSaying.id = ++lastId
            wiseSayings.add(wiseSaying)
        }

        return wiseSaying
    }

    override fun isEmpty(): Boolean {
        return wiseSayings.isEmpty()
    }

    override fun findAll(): List<WiseSaying> {
        return wiseSayings.reversed()
    }

    override fun findById(id: Int): WiseSaying? {
        return wiseSayings.firstOrNull { it.id == id }
    }

    override fun delete(wiseSaying: WiseSaying) {
        wiseSayings.remove(wiseSaying)
    }

    override fun clear() {
        lastId = 0
        wiseSayings.clear()
    }

    override fun build() {}

    override fun findByAuthorLike(authorLike: String): List<WiseSaying> {
        val pureKeyword = authorLike.replace("%", "")

        if (pureKeyword.isBlank()) {
            return wiseSayings
        }

        return if (authorLike.startsWith("%") && authorLike.endsWith("%")) {
            findAll().filter { it.author.contains(pureKeyword) }
        } else if (authorLike.startsWith("%")) {
            findAll().filter { it.author.endsWith(pureKeyword) }
        } else if (authorLike.endsWith("%")) {
            findAll().filter { it.author.startsWith(pureKeyword) }
        } else {
            findAll().filter { it.author == pureKeyword }
        }
    }

    override fun findByAuthorContent(contentLike: String): List<WiseSaying> {
        val pureKeyword = contentLike.replace("%", "")

        if (pureKeyword.isBlank()) {
            return wiseSayings
        }

        return if (contentLike.startsWith("%") && contentLike.endsWith("%")) {
            findAll().filter { it.content.contains(pureKeyword) }
        } else if (contentLike.startsWith("%")) {
            findAll().filter { it.content.endsWith(pureKeyword) }
        } else if (contentLike.endsWith("%")) {
            findAll().filter { it.content.startsWith(pureKeyword) }
        } else {
            findAll().filter { it.content == pureKeyword }
        }
    }

    override fun findAllPaged(itemsPerPage: Int, pageNo: Int): Page<WiseSaying> {
        val content = findAll()
            .drop((pageNo - 1) * itemsPerPage)
            .take(itemsPerPage)

        return Page(wiseSayings.size, itemsPerPage, pageNo, "", "", content)
    }

    override fun findByKeywordPaged(
        keywordType: String,
        keyword: String,
        itemsPerPage: Int,
        pageNo: Int
    ): Page<WiseSaying> {
        return when (keywordType) {
            "author" -> findByAuthorLike("%$keyword%")
            else -> findByAuthorContent("%$keyword%")
        }.let {
            val content = it
                .drop((pageNo - 1) * itemsPerPage)
                .take(itemsPerPage)

            Page(it.size, itemsPerPage, pageNo, keywordType, keyword, content)
        }
    }
}