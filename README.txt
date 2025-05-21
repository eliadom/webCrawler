*******************************************
************** CODE COMMENTS **************
*******************************************
My commenting technique is:

// Pre: What the arguments should be like for the function to work properly
// Post: Explanation of what the function returns
// Action: Important action performed that is not returned. For example, changing a file or
if there was database interaction, implicit actions like modifying a database register.

**********************************************
************** CLEANING METHODS **************
**********************************************
I tried to check the official API methods (https://github.com/HackerNews/API) but could not find the call of getting
most recent "things" in general, counting ask, show, jobs... Then I checked network traffic and realized
we were getting the HTML itself. The methods I used for "cleaning" the HTML are:

- cleanedElements(doc)
- unifyElements(cleanElements)
- hnEntriesFromElements(cleanElements)
- HNEntry(element)

The Jsoup methods I have been using for finding elements are not hierarchical. They analyze the whole document and
return all elements that match, and they do not stop if both a parent and a child match. They will return both.
That's why my process of narrowing elements needs a proper guide.

** EXPLANATION

---- cleanedElements(doc)
The main table had id="hnmain". This helped me filter by levels.
That table contains 3 tables more with attributes border="0" cellpadding="0" cellspacing="0"
By filtering those 3 elements we will always have an array of 3 elements:
[0] is the big "hnmain" table itself
[1] is the vertical menu
[2] is our main data table
We pick the 3rd element and get the big information table.
Out of those, the "table > tbody > tr" elements are either:
- HTML element with rank, score and title
- HTML element with subtext (points and comments). Points or comments may be missing but the structure is the same.
They always follow that format and are being alternated constantly. There cannot be 2 HTML elements with the same format
next to each other.

---- unifyElements(cleanElement)
This function simply joins the 2 matching HTML elements explained previously.

---- hnEntriesFromElements(cleanElements)
Creates new HNEntry with custom Jsoup Element constructor.

---- HNEntry(element)
The entry gets created from an element that contains the 2 HTML mentioned before.
- number is always the text content of the HTML with class "rank". First HTML.
- title is always the text content of the HTML with class "titleline". First HTML
- points are contained on the text of the HTML with class "score". Second HTML. Format is "x points" so we keep the
first part as a number for sorting.
- comments are contained on the text of the HTML with class "subtext". Second HTML. Format is "x comments" so we keep the
first part as a number for sorting.


*************************************
************** TESTING **************
*************************************

I have used JUnit and Mockito for testing as those are the testing frameworks I feel confident with.
I purposely excluded from testing:

* Main.java
* Private methods
* Printing methods





--- Elia