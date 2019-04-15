package com.ossp.database.controller

import com.ossp.database.model.AuthInfo
import com.ossp.database.model.Session
import com.ossp.database.model.User
import com.ossp.database.model.UserSession
import com.ossp.database.service.session.SessionService
import com.ossp.database.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
class UserController {

    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val sessionService: SessionService? = null

    @RequestMapping(path = ["/users"])
    fun list(): List<User>? {
        return userService?.list()
    }

    @RequestMapping(path = ["/users/{id}"])
    operator fun get(@PathVariable("id") id: Long?): User? {
        userService?.findById(id)?.let {
            return it
        } ?: run {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User with id $id not found")
        }
    }

    @RequestMapping(path = ["/users/{username}"])
    fun getByUsername(@PathVariable("username") username: String): AuthInfo {
        val user = userService?.findByUsername(username)

        user?.let {
            return AuthInfo(it.username, it.password)
        } ?: run {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User with username $username not found")
        }
    }

    @RequestMapping(path = ["/users"], method = [RequestMethod.POST])
    fun createUser(@RequestBody userSession: UserSession): UserSession? {
        var user: User? = null

        userSession.username?.let { username ->
            userService?.findByUsername(username)?.let {
                user = it
            } ?: run {
                user = userService?.create(makeUserFromRequest(userSession))
            }
            val session: Session? = sessionService?.create(makeSessionFromRequest(user?.userId, userSession))
            return UserSession(
                    userId = user?.userId,
                    sessionId = session?.sessionId,
                    role = user?.role,
                    name = user?.name,
                    username = user?.username,
                    userCreatedDate = user?.createdDate,
                    sessionCreatedDate = session?.createdDate,
                    refreshToken = session?.refreshToken,
                    accessToken = session?.accessToken,
                    expiresIn = session?.expiresIn,
                    tokenType = session?.tokenType,
                    password = null
            )
        } ?: run {
            return null
        }
    }

    private fun makeUserFromRequest(userSession: UserSession): User {
        val newUser = User()
        newUser.createdDate = Date()
        newUser.name = userSession.name
        newUser.password = userSession.password
        newUser.role = userSession.role
        newUser.username = userSession.username
        return newUser
    }

    private fun makeSessionFromRequest(userId: Long?, userSession: UserSession): Session {
        val newSession = Session()
        newSession.userId = userId
        newSession.accessToken = userSession.accessToken
        newSession.createdDate = Date()
        newSession.expiresIn = userSession.expiresIn
        newSession.refreshToken = userSession.refreshToken
        newSession.tokenType = userSession.tokenType
        return newSession
    }
}