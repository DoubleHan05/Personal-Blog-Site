# Data Base Design

### **一、数据库表结构设计**

#### **1. 用户表（users）**
存储用户基本信息及认证数据。
| 字段名          | 类型           | 约束/说明                          |
|-----------------|----------------|-----------------------------------|
| id              | INT UNSIGNED   | 主键，自增                         |
| username        | VARCHAR(50)    | 唯一，非空（用户登录名）          |
| password        | VARCHAR(255)   | 非空（加密存储）   |
| email           | VARCHAR(100)   | 唯一，非空（用于注册验证）         |
| nickname        | VARCHAR(50)    | 可选（用户显示名称）               |
| avatar          | VARCHAR(200)   | 可选（头像URL）                    |
| bio             | TEXT           | 可选（个人简介）                   |
| is_active       | TINYINT(1)     | 默认1（是否激活，0=未激活）        |
| is_admin        | TINYINT(1)     | 默认0（是否管理员，1=是）          |
| created_at      | DATETIME       | 默认CURRENT_TIMESTAMP（注册时间）  |
| last_login_at   | DATETIME       | 可选（最后登录时间）               |

**索引**：`username`、`email` 设为唯一索引。


#### **2. 博客文章表（articles）**
存储博客文章内容及元数据。
| 字段名          | 类型           | 约束/说明                          |
|-----------------|----------------|-----------------------------------|
| id              | INT UNSIGNED   | 主键，自增                         |
| title           | VARCHAR(200)   | 非空（文章标题）                   |
| content         | TEXT           | 非空（文章内容）                   |
| status          | VARCHAR(20)    | 默认'draft'（状态：draft/ published/ archived） |
| author_id       | INT UNSIGNED   | 非空（外键，关联users.id）         |
| published_at    | DATETIME       | 可选（发布时间，仅发布状态时有值） |
| updated_at      | DATETIME       | 默认CURRENT_TIMESTAMP（更新时间）  |
| view_count      | INT UNSIGNED   | 默认0（浏览量）                    |
| summary         | TEXT           | 可选（文章摘要）                   |

**关系**：`author_id` 关联 `users.id`（一对多，用户发布多篇文章）。


#### **3. 分类表（categories）**
存储文章分类信息（如技术、生活、感悟）。
| 字段名          | 类型           | 约束/说明                          |
|-----------------|----------------|-----------------------------------|
| id              | INT UNSIGNED   | 主键，自增                         |
| name            | VARCHAR(50)    | 唯一，非空（分类名称）             |
| description     | VARCHAR(200)   | 可选（分类描述）                   |
| created_at      | DATETIME       | 默认CURRENT_TIMESTAMP（创建时间）  |

**关系**：与文章表通过中间表 `article_categories` 建立多对多关联。


#### **4. 标签表（tags）**
存储文章标签（如MySQL、Python、前端）。
| 字段名          | 类型           | 约束/说明                          |
|-----------------|----------------|-----------------------------------|
| id              | INT UNSIGNED   | 主键，自增                         |
| name            | VARCHAR(50)    | 唯一，非空（标签名称）             |
| created_at      | DATETIME       | 默认CURRENT_TIMESTAMP（创建时间）  |

**关系**：与文章表通过中间表 `article_tags` 建立多对多关联。


#### **5. 文章-分类关联表（article_categories）**
建立文章与分类的多对多关系。
| 字段名          | 类型           | 约束/说明                          |
|-----------------|----------------|-----------------------------------|
| article_id      | INT UNSIGNED   | 主键，外键（关联articles.id）     |
| category_id     | INT UNSIGNED   | 主键，外键（关联categories.id）   |

**联合主键**：`(article_id, category_id)`。


#### **6. 文章-标签关联表（article_tags）**
建立文章与标签的多对多关系。
| 字段名          | 类型           | 约束/说明                          |
|-----------------|----------------|-----------------------------------|
| article_id      | INT UNSIGNED   | 主键，外键（关联articles.id）     |
| tag_id          | INT UNSIGNED   | 主键，外键（关联tags.id）         |

**联合主键**：`(article_id, tag_id)`。


#### **7. 评论表（comments）**
存储用户对文章的评论及回复（支持多级评论）。
| 字段名          | 类型           | 约束/说明                          |
|-----------------|----------------|-----------------------------------|
| id              | INT UNSIGNED   | 主键，自增                         |
| content         | TEXT           | 非空（评论内容）                   |
| user_id         | INT UNSIGNED   | 非空（外键，关联users.id）         |
| article_id      | INT UNSIGNED   | 非空（外键，关联articles.id）     |
| parent_id       | INT UNSIGNED   | 可选（外键，关联自身id，多级评论） |
| is_approved     | TINYINT(1)     | 默认1（是否审核通过，0=未通过）    |
| created_at      | DATETIME       | 默认CURRENT_TIMESTAMP（评论时间）  |

**关系**：
- `user_id` 关联 `users.id`（评论者）。
- `article_id` 关联 `articles.id`（评论所属文章）。
- `parent_id` 自关联 `comments.id`（实现父子评论，如回复评论）。