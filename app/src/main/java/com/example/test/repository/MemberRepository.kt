package com.example.test.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.test.database.member.MemberDatabase
import com.example.test.database.member.asDomainModel
import com.example.test.domain.Member
import com.example.test.network.asDatabaseMember
import com.example.test.network.interfaces.ApiMemberObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class MemberRepository(private val database: MemberDatabase) {
    // LiveData object with Members from the database transformed to domain Members
    val members: LiveData<List<Member>> =
        Transformations.map(database.memberDatabaseDao.getAllMembersLive()) {
            it.asDomainModel()
        }

    // Network call to refresh the Members
    suspend fun refreshMembers() {
        // Switch the context to an IO thread
        withContext(Dispatchers.IO) {
            val apiMember = ApiMemberObj.retrofitService.getAllMembers().await()
            // '*': Kotlin spread operator, used for functions that expect a vararg param and this just spreads the array into separate fields
            database.memberDatabaseDao.insertAll(*apiMember.members.asDatabaseMember())

            Timber.i("Refreshed members from the API and saved them in the database")
        }
    }
}
