package com.AlMLand.customConnectArchetype.model

internal val gitHubIssueJson = """
            {
                "url": "https://api.github.com/repos/kubernetes/kubernetes/issues/116952",
                "repository_url": "https://api.github.com/repos/kubernetes/kubernetes",
                "labels_url": "https://api.github.com/repos/kubernetes/kubernetes/issues/116952/labels{/name}",
                "comments_url": "https://api.github.com/repos/kubernetes/kubernetes/issues/116952/comments",
                "events_url": "https://api.github.com/repos/kubernetes/kubernetes/issues/116952/events",
                "html_url": "https://github.com/kubernetes/kubernetes/pull/116952",
                "id": 1642651252,
                "node_id": "PR_kwDOAToIks5NACW9",
                "number": 116952,
                "title": "ktesting: add object formatter",
                "user": {
                    "login": "pohly",
                    "id": 22076,
                    "node_id": "MDQ6VXNlcjIyMDc2",
                    "avatar_url": "https://avatars.githubusercontent.com/u/22076?v=4",
                    "gravatar_id": "",
                    "url": "https://api.github.com/users/pohly",
                    "html_url": "https://github.com/pohly",
                    "followers_url": "https://api.github.com/users/pohly/followers",
                    "following_url": "https://api.github.com/users/pohly/following{/other_user}",
                    "gists_url": "https://api.github.com/users/pohly/gists{/gist_id}",
                    "starred_url": "https://api.github.com/users/pohly/starred{/owner}{/repo}",
                    "subscriptions_url": "https://api.github.com/users/pohly/subscriptions",
                    "organizations_url": "https://api.github.com/users/pohly/orgs",
                    "repos_url": "https://api.github.com/users/pohly/repos",
                    "events_url": "https://api.github.com/users/pohly/events{/privacy}",
                    "received_events_url": "https://api.github.com/users/pohly/received_events",
                    "type": "User",
                    "site_admin": false
                },
            "labels": [
                {
                    "id": 253450934,
                    "node_id": "MDU6TGFiZWwyNTM0NTA5MzQ=",
                    "url": "https://api.github.com/repos/kubernetes/kubernetes/labels/size/M",
                    "name": "size/M",
                    "color": "eebb00",
                    "default": false,
                    "description": "Denotes a PR that changes 30-99 lines, ignoring generated files."
                },
                {
                    "id": 267761362,
                    "node_id": "MDU6TGFiZWwyNjc3NjEzNjI=",
                    "url": "https://api.github.com/repos/kubernetes/kubernetes/labels/kind/feature",
                    "name": "kind/feature",
                    "color": "c7def8",
                    "default": false,
                    "description": "Categorizes issue or PR as related to a new feature."
                },
                {
                    "id": 349530249,
                    "node_id": "MDU6TGFiZWwzNDk1MzAyNDk=",
                    "url": "https://api.github.com/repos/kubernetes/kubernetes/labels/release-note-none",
                    "name": "release-note-none",
                    "color": "c2e0c6",
                    "default": false,
                    "description": "Denotes a PR that doesn't merit a release note."
                },
                {
                    "id": 477397086,
                    "node_id": "MDU6TGFiZWw0NzczOTcwODY=",
                    "url": "https://api.github.com/repos/kubernetes/kubernetes/labels/cncf-cla:%20yes",
                    "name": "cncf-cla: yes",
                    "color": "bfe5bf",
                    "default": false,
                    "description": "Indicates the PR's author has signed the CNCF CLA."
                },
                {
                    "id": 1111992057,
                    "node_id": "MDU6TGFiZWwxMTExOTkyMDU3",
                    "url": "https://api.github.com/repos/kubernetes/kubernetes/labels/needs-priority",
                    "name": "needs-priority",
                    "color": "ededed",
                    "default": false,
                    "description": "Indicates a PR lacks a `priority/foo` label and requires one."
                },
                {
                    "id": 2389815605,
                    "node_id": "MDU6TGFiZWwyMzg5ODE1NjA1",
                    "url": "https://api.github.com/repos/kubernetes/kubernetes/labels/needs-triage",
                    "name": "needs-triage",
                    "color": "ededed",
                    "default": false,
                    "description": "Indicates an issue or PR lacks a `triage/foo` label and requires one."
                },
                {
                    "id": 2480789133,
                    "node_id": "MDU6TGFiZWwyNDgwNzg5MTMz",
                    "url": "https://api.github.com/repos/kubernetes/kubernetes/labels/do-not-merge/needs-sig",
                    "name": "do-not-merge/needs-sig",
                    "color": "e11d21",
                    "default": false,
                    "description": "Indicates an issue or PR lacks a `sig/foo` label and requires one."
                }
            ],
                "state": "open",
                "locked": false,
                "assignee": null,
                "assignees": [],
                "milestone": null,
                "comments": 2,
                "created_at": "2023-03-27T19:02:16Z",
                "updated_at": "2023-03-27T19:04:55Z",
                "closed_at": null,
                "author_association": "CONTRIBUTOR",
                "active_lock_reason": null,
                "draft": false,
                "pull_request": {
                    "url": "https://api.github.com/repos/kubernetes/kubernetes/pulls/116952",
                    "html_url": "https://github.com/kubernetes/kubernetes/pull/116952",
                    "diff_url": "https://github.com/kubernetes/kubernetes/pull/116952.diff",
                    "patch_url": "https://github.com/kubernetes/kubernetes/pull/116952.patch",
                    "merged_at": null
                },
            "body": "#### What type of PR is this?\r\n\r\n/kind feature\r\n\r\n#### What this PR does / why we need it:\r\n\r\nThis is useful for logging types which implement String incorrectly, for example by inheriting it from metav1.TypeMeta. If implementing fmt.Stringer and logr.Marshaler for the type is not possible or not desirable, then log calls can wrap an instance of the type or a pointer to it with Format. That then returns a wrapper which implements both.\r\n\r\nYAML formatting is used for pretty-printing to a multi-line string.\r\n\r\n#### Which issue(s) this PR fixes:\r\nRelated-to: https://github.com/kubernetes/kubernetes/pull/115950\r\n\r\n#### Special notes for your reviewer:\r\n\r\n#### Does this PR introduce a user-facing change?\r\n```release-note\r\nNONE\r\n```\r\n\r\n/cc @SataQiu @liggitt ",
            "reactions": {
                "url": "https://api.github.com/repos/kubernetes/kubernetes/issues/116952/reactions",
                "total_count": 0,
                "+1": 0,
                "-1": 0,
                "laugh": 0,
                "hooray": 0,
                "confused": 0,
                "heart": 0,
                "rocket": 0,
                "eyes": 0
            },
                "timeline_url": "https://api.github.com/repos/kubernetes/kubernetes/issues/116952/timeline",
                "performed_via_github_app": null,
                "state_reason": null
            }
        """.trimIndent()
internal val gitHubPullRequestJson = """
            {
                "url": "https://api.github.com/repos/kubernetes/kubernetes/pulls/116869",
                "html_url": "https://github.com/kubernetes/kubernetes/pull/116869",
                "diff_url": "https://github.com/kubernetes/kubernetes/pull/116869.diff",
                "patch_url": "https://github.com/kubernetes/kubernetes/pull/116869.patch",
                "merged_at": null
            }
        """.trimIndent()
internal val gitHubLabelJson = """
            {
                "id": 253450934,
                "node_id": "MDU6TGFiZWwyNTM0NTA5MzQ=",
                "url": "https://api.github.com/repos/kubernetes/kubernetes/labels/size/M",
                "name": "size/M",
                "color": "eebb00",
                "default": false,
                "description": "Denotes a PR that changes 30-99 lines, ignoring generated files."
            }
        """.trimIndent()
internal val gitHubReactionsJson = """
            {
                "url": "https://api.github.com/repos/kubernetes/kubernetes/issues/116952/reactions",
                "total_count": 0,
                "+1": 0,
                "-1": 0,
                "laugh": 0,
                "hooray": 0,
                "confused": 0,
                "heart": 0,
                "rocket": 0,
                "eyes": 0
            }
        """.trimIndent()
internal val gitHubUserJson = """
            {
                "login": "pohly",
                "id": 22076,
                "node_id": "MDQ6VXNlcjIyMDc2",
                "avatar_url": "https://avatars.githubusercontent.com/u/22076?v=4",
                "gravatar_id": "",
                "url": "https://api.github.com/users/pohly",
                "html_url": "https://github.com/pohly",
                "followers_url": "https://api.github.com/users/pohly/followers",
                "following_url": "https://api.github.com/users/pohly/following{/other_user}",
                "gists_url": "https://api.github.com/users/pohly/gists{/gist_id}",
                "starred_url": "https://api.github.com/users/pohly/starred{/owner}{/repo}",
                "subscriptions_url": "https://api.github.com/users/pohly/subscriptions",
                "organizations_url": "https://api.github.com/users/pohly/orgs",
                "repos_url": "https://api.github.com/users/pohly/repos",
                "events_url": "https://api.github.com/users/pohly/events{/privacy}",
                "received_events_url": "https://api.github.com/users/pohly/received_events",
                "type": "User",
                "site_admin": false
            }
        """.trimIndent()
internal val gitHubAssigneeJson = """
            {
                "login": "mborsz",
                "id": 2559168,
                "node_id": "MDQ6VXNlcjI1NTkxNjg=",
                "avatar_url": "https://avatars.githubusercontent.com/u/2559168?v=4",
                "gravatar_id": "",
                "url": "https://api.github.com/users/mborsz",
                "html_url": "https://github.com/mborsz",
                "followers_url": "https://api.github.com/users/mborsz/followers",
                "following_url": "https://api.github.com/users/mborsz/following{/other_user}",
                "gists_url": "https://api.github.com/users/mborsz/gists{/gist_id}",
                "starred_url": "https://api.github.com/users/mborsz/starred{/owner}{/repo}",
                "subscriptions_url": "https://api.github.com/users/mborsz/subscriptions",
                "organizations_url": "https://api.github.com/users/mborsz/orgs",
                "repos_url": "https://api.github.com/users/mborsz/repos",
                "events_url": "https://api.github.com/users/mborsz/events{/privacy}",
                "received_events_url": "https://api.github.com/users/mborsz/received_events",
                "type": "User",
                "site_admin": false
            }
        """.trimIndent()
