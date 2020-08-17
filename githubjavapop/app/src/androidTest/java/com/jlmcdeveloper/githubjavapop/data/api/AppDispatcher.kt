package com.jlmcdeveloper.githubjavapop.data.api

import com.jlmcdeveloper.githubjavapop.data.api.model.RepositoryListResponse
import com.jlmcdeveloper.githubjavapop.data.model.PullRequest
import com.jlmcdeveloper.githubjavapop.data.model.User
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class AppDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {

        return when(request.path){
            //============================= Repository =====================================
            "/search/repositories?q=language%3A${ApiEndPoint.language}" +
                    "&sort=${ApiEndPoint.sort}" +
                    "&per_page=${ApiEndPoint.perPage}" +
                    "&page=${1}" -> MockResponse().setResponseCode(200).setBody(
                """{
                  "total_count": 6812276,
                  "incomplete_results": true,
                  "items": [
                    {
                      "id": $id,
                      "node_id": "MDEwOlJlcG9zaXRvcnkyMjc5MDQ4OA==",
                      "name": "$name",
                      "full_name": "$full_name",
                      "private": false,
                      "owner": {
                        "login": "${owner.login}",
                        "node_id": "MDQ6VXNlcjU4MjM0Ng==",
                        "avatar_url": "${owner.avatarUrl}",
                        "gravatar_id": "",
                        "url": "https://api.github.com/users/iluwatar",
                        "html_url": "${owner.htmlUrl}",
                        "type": "User",
                        "site_admin": false
                      },
                      "html_url": "https://github.com/iluwatar/java-design-patterns",
                      "description": "$description",
                      "fork": false,
                      "url": "https://api.github.com/repos/iluwatar/java-design-patterns",
                      "homepage": "https://java-design-patterns.com",
                      "size": 22183,
                      "stargazers_count": $stargazers_count,
                      "watchers_count": 59883,
                      "language": "Java",
                      "has_issues": true,
                      "has_projects": false,
                      "has_downloads": true,
                      "has_wiki": true,
                      "has_pages": false,
                      "forks_count": $forks_count
                    }
                  ]
                }""".trimEnd())

            //-------- falha erro corpo vazio  --------
            "/search/repositories?q=language%3A${ApiEndPoint.language}" +
                    "&sort=${ApiEndPoint.sort}" +
                    "&per_page=${ApiEndPoint.perPage}" +
                    "&page=${2}" -> MockResponse().setResponseCode(204)




            //============================= PullRequest =====================================
            "/repos/${pull.user}/${pull.repository}/pulls?per_page=${ApiEndPoint.perPage}" +
            "&page=${1}" -> MockResponse().setResponseCode(200).setBody(
                """[
                  {
                    "id": 467231811,
                    "node_id": "MDExOlB1bGxSZXF1ZXN0NDY3MjMxODEx",
                    "html_url": "${pull.request.url}",
                    "diff_url": "https://github.com/CyC2018/CS-Notes/pull/969.diff",
                    "patch_url": "https://github.com/CyC2018/CS-Notes/pull/969.patch",
                    "issue_url": "https://api.github.com/repos/CyC2018/CS-Notes/issues/969",
                    "number": 969,
                    "state": "open",
                    "locked": false,
                    "title": "${pull.request.title}",
                    "user": {
                      "login": "${pull.request.user.name}",
                      "id": 26364818,
                      "node_id": "MDQ6VXNlcjI2MzY0ODE4",
                      "avatar_url": "${pull.request.user.photo}",
                      "gravatar_id": "",
                      "url": "https://api.github.com/users/i-Hu",
                      "html_url": "https://github.com/i-Hu",
                      "followers_url": "https://api.github.com/users/i-Hu/followers",
                      "following_url": "https://api.github.com/users/i-Hu/following{/other_user}",
                      "gists_url": "https://api.github.com/users/i-Hu/gists{/gist_id}",
                      "starred_url": "https://api.github.com/users/i-Hu/starred{/owner}{/repo}",
                      "subscriptions_url": "https://api.github.com/users/i-Hu/subscriptions",
                      "organizations_url": "https://api.github.com/users/i-Hu/orgs",
                      "repos_url": "https://api.github.com/users/i-Hu/repos",
                      "events_url": "https://api.github.com/users/i-Hu/events{/privacy}",
                      "received_events_url": "https://api.github.com/users/i-Hu/received_events",
                      "type": "User",
                      "site_admin": false
                    },
                    "body": "${pull.request.body}",
                    "created_at": "2020-08-13T08:27:38Z",
                    "updated_at": "2020-08-13T08:27:38Z",
                    "milestone": null,
                    "draft": false,
                    "commits_url": "https://api.github.com/repos/CyC2018/CS-Notes/pulls/969/commits",
                    "review_comments_url": "https://api.github.com/repos/CyC2018/CS-Notes/pulls/969/comments",
                    "review_comment_url": "https://api.github.com/repos/CyC2018/CS-Notes/pulls/comments{/number}",
                    "comments_url": "https://api.github.com/repos/CyC2018/CS-Notes/issues/969/comments",
                    "statuses_url": "https://api.github.com/repos/CyC2018/CS-Notes/statuses/b6c7625c6fb39656d49558672355841eea9d5b65",
                    "base": {
                      "label": "CyC2018:master",
                      "ref": "master",
                      "sha": "1392056d73b28a15a17e2937df4129d09fc24e07",
                      "repo": {
                        "id": 121395510,
                        "node_id": "MDEwOlJlcG9zaXRvcnkxMjEzOTU1MTA=",
                        "name": "CS-Notes",
                        "full_name": "CyC2018/CS-Notes",
                        "private": false,
                        "html_url": "https://github.com/CyC2018/CS-Notes",
                        "description": ":books: 技术面试必备基础知识、Leetcode、计算机操作系统、计算机网络、系统设计、Java、Python、C++",
                        "fork": false,
                        "url": "https://api.github.com/repos/CyC2018/CS-Notes",
                        "forks_url": "https://api.github.com/repos/CyC2018/CS-Notes/forks",
                        "keys_url": "https://api.github.com/repos/CyC2018/CS-Notes/keys{/key_id}",
                        "collaborators_url": "https://api.github.com/repos/CyC2018/CS-Notes/collaborators{/collaborator}",
                        "teams_url": "https://api.github.com/repos/CyC2018/CS-Notes/teams",
                        "hooks_url": "https://api.github.com/repos/CyC2018/CS-Notes/hooks",
                        "issue_events_url": "https://api.github.com/repos/CyC2018/CS-Notes/issues/events{/number}",
                        "events_url": "https://api.github.com/repos/CyC2018/CS-Notes/events",
                        "assignees_url": "https://api.github.com/repos/CyC2018/CS-Notes/assignees{/user}",
                        "branches_url": "https://api.github.com/repos/CyC2018/CS-Notes/branches{/branch}",
                        "tags_url": "https://api.github.com/repos/CyC2018/CS-Notes/tags",
                        "blobs_url": "https://api.github.com/repos/CyC2018/CS-Notes/git/blobs{/sha}",
                        "git_tags_url": "https://api.github.com/repos/CyC2018/CS-Notes/git/tags{/sha}",
                        "git_refs_url": "https://api.github.com/repos/CyC2018/CS-Notes/git/refs{/sha}",
                        "trees_url": "https://api.github.com/repos/CyC2018/CS-Notes/git/trees{/sha}",
                        "statuses_url": "https://api.github.com/repos/CyC2018/CS-Notes/statuses/{sha}",
                        "languages_url": "https://api.github.com/repos/CyC2018/CS-Notes/languages",
                        "stargazers_url": "https://api.github.com/repos/CyC2018/CS-Notes/stargazers",
                        "contributors_url": "https://api.github.com/repos/CyC2018/CS-Notes/contributors",
                        "subscribers_url": "https://api.github.com/repos/CyC2018/CS-Notes/subscribers",
                        "subscription_url": "https://api.github.com/repos/CyC2018/CS-Notes/subscription",
                        "commits_url": "https://api.github.com/repos/CyC2018/CS-Notes/commits{/sha}",
                        "git_commits_url": "https://api.github.com/repos/CyC2018/CS-Notes/git/commits{/sha}",
                        "comments_url": "https://api.github.com/repos/CyC2018/CS-Notes/comments{/number}",
                        "issue_comment_url": "https://api.github.com/repos/CyC2018/CS-Notes/issues/comments{/number}",
                        "contents_url": "https://api.github.com/repos/CyC2018/CS-Notes/contents/{+path}",
                        "compare_url": "https://api.github.com/repos/CyC2018/CS-Notes/compare/{base}...{head}",
                        "merges_url": "https://api.github.com/repos/CyC2018/CS-Notes/merges",
                        "archive_url": "https://api.github.com/repos/CyC2018/CS-Notes/{archive_format}{/ref}",
                        "downloads_url": "https://api.github.com/repos/CyC2018/CS-Notes/downloads",
                        "issues_url": "https://api.github.com/repos/CyC2018/CS-Notes/issues{/number}",
                        "pulls_url": "https://api.github.com/repos/CyC2018/CS-Notes/pulls{/number}",
                        "milestones_url": "https://api.github.com/repos/CyC2018/CS-Notes/milestones{/number}",
                        "notifications_url": "https://api.github.com/repos/CyC2018/CS-Notes/notifications{?since,all,participating}",
                        "labels_url": "https://api.github.com/repos/CyC2018/CS-Notes/labels{/name}",
                        "releases_url": "https://api.github.com/repos/CyC2018/CS-Notes/releases{/id}",
                        "deployments_url": "https://api.github.com/repos/CyC2018/CS-Notes/deployments",
                        "created_at": "2020-08-17T01:38:14Z",
                        "updated_at": "${pull.request.user.userInfo}",
                        "pushed_at": "2020-08-13T08:27:38Z",
                        "git_url": "git://github.com/CyC2018/CS-Notes.git",
                        "ssh_url": "git@github.com:CyC2018/CS-Notes.git",
                        "clone_url": "https://github.com/CyC2018/CS-Notes.git",
                        "svn_url": "https://github.com/CyC2018/CS-Notes",
                        "homepage": "https://cyc2018.github.io/CS-Notes",
                        "size": 111772,
                        "stargazers_count": 108609,
                        "watchers_count": 108609,
                        "language": "Java",
                        "has_issues": true,
                        "has_projects": true,
                        "has_downloads": true,
                        "has_wiki": true,
                        "has_pages": true,
                        "forks_count": 35461,
                        "mirror_url": null,
                        "archived": false,
                        "disabled": false,
                        "open_issues_count": 59,
                        "license": null,
                        "forks": 35461,
                        "open_issues": 59,
                        "watchers": 108609,
                        "default_branch": "master"
                      }
                    }
                  }
                ]
            """.trimIndent())



            //-------- falha erro corpo vazio  --------
            "/repos/${pull.user}/${pull.repository}/pulls?per_page=${ApiEndPoint.perPage}" +
                    "&page=${2}" -> MockResponse().setResponseCode(204)



            // -------------- error fatal ------------------
            else -> MockResponse().setResponseCode(404)
        }
    }

    companion object {

        const val id = "121395510"
        const val size = 1
        const val name = "CS-Notes"
        const val full_name ="CyC2018/CS-Notes"
        const val description = "books: 技术面试必备基础知识、Leetcode、计算机操作系统、计算机网络、系统设计、Java、Python、C++"
        const val forks_count = "35414"
        const val stargazers_count = "108485"
        val owner = RepositoryListResponse.Owner(
            login = "CyC2018",
            avatarUrl = "https://avatars3.githubusercontent.com/u/36260787?v=4",
            htmlUrl = "https://github.com/CyC2018"
        )


        val pull = Pull("CyC2018",
            "CS-Notes",
            PullRequest(title = "Update 数据库系统原理.md",
                body = "修改一个错别字",
                url = "https://github.com/CyC2018/CS-Notes/pulls/969",
            user= User(name = "i-Hu",
                userInfo = "2018-02-13T14:56:24Z",
                photo = "https://avatars0.githubusercontent.com/u/26364818?v=4")))
    }

    data class Pull(val user: String, val repository: String, val request: PullRequest)
}