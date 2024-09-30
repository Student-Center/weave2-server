package com.threedays.domain.user.repository

import com.threedays.domain.user.entity.User
import com.threedays.support.common.base.domain.Repository

interface UserRepository : Repository<User, User.Id>
