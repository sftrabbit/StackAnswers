# Design

When it comes to designing Android applications, the [Android
Design](http://developer.android.com/design/index.html) guidelines are
gospel. StackSync must be intricately crafted to meet the expectations
of an exceptional application.

## Structure

StackSync has a fairly deep hierarchical structure due to the complexity
of the Stack Exchange network. Within this structure there are a range
of different views of data. However, the application can easily be
divided into two distinct levels of data:

1.  **Network level** - Here, the user can browse and manage the network
    of question-and-answer sites. They will receive site-independent
    notifications and messages and can view network-wide data.
    This is not the level where the user will spend most of their time,
    so mechanisms for jumping to the relevant sections of the next level
    are necessary. The user can also manage their accounts at this level.
2.  **Site level** - Here, the user can browse the content of an
    individual Stack Exchange site, e.g. Stack Overflow. They will
    receive site-specific notifications and messages. This content is
    divided into two major sections:
    1.  The vast majority of site level content is questions, their
        answers, and comments. The user must be able to filter and
        search this content. For example, the user may want to view only
        questions with a certain tag.
    2.  Each Stack Exchange user has a profile per site that contains
        information about that user, such as their reputation, questions
        and answers, personal information, and so on. The user must be
        able to view profiles of other users for any specific site. They
        must also be able to view their own profile easily and edit any
        personal content. The profile of a user is often reached through
        that user's posted content.

The site level section provides two lists: one of Stack Exchange sites
and the other of the user's accounts. Each of these allow the user to
jump into the site level, which is in a sense the detail view of those
list items.

At the site level, both the question and user sections have a similar
structure. They each provide a searchable and filterable list of their
content (list of questions; list of users) and a detailed view of that
content (question and its answers; user's profile).

In addition to these main sections, there are ancillary sections to help
the user manage the application itself. For example, there will likely be
a number of preferences for the user to customise the application.

### Structure checklist

*   The start screen should put content forward - new content, favourite
    content, etc. It should gradually come to know the user and present
    them with interesting content.
*   At the top level, the action bar should display 'StackSync' and the
    StackSync logo.
*   A search action should be included in the action bar in all
    searchable sections.
*   Make browsing the hierarchy of data easier by providing horizontal
    navigation and shortcuts.

## Navigation

### Navigation checklist

*   The network level provides relatively unrelated views and so should
    provide fixed tabs for switching between them.
*   For different views of site level data, provide a spinner in the
    action bar.
*   Make sure the back stack and the Up button are predictable.
