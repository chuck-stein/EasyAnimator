~~~~~~~~~~~~~~REVIEW OF OUR EXPERIENCE~~~~~~~~~~~~~~

Our experience with Assignment 8 started out very rocky, but we managed to overcome an initial
hurdle and what remained was an effective and informative experience in adapting code from
other programmers to work with our application (an important collaborative aspect of the industry).
Our original provider had been working alone for several weeks, and the code we were given was
reflective of that burden, as it was an incomplete design with multiple compile errors. We saw no
way to adapt this to our program or request enough changes to make it adaptable by the submission
deadline, so we spoke to Professor Lerner and found a solution. We wrote a professional code review
of the provided code, and were given a new provider group, with the caveat that they are also our
new customers in addition to our original customers group.

Luckily we managed providing for two different groups without too much difficulty. My partner
communicated with the new customers while I communicated with the original customers, and we were
mostly just answering questions, since the customers didn't require many code changes. We learned to
always extensively review a complex program before providing it to a client, since there are
countless little mistakes that can be overlooked, such as typing 'Y' instead of 'X'. We also learned
from our new providers' code that our controller was slightly coupled to our view in the way we
displayed error popups (assuming a animator view was a JFrame, when it could be an adapter/other GUI).
Additionally, their code showed us that other students interpreted the assignments similar to how we
did, in terms of comparable data structures and listener interfaces.

Based on our experience with Assignment 8, we wish we had designed our program differently by using
the Timer from the Swing library instead of the Java.util package. Our original provider mentioned a
Swing Timer in their code, which we did not know existed as a separate tool from Java.util.Timer.
After reviewing the documentation, the Swing timer seems like a much better fit for this
application, since it has methods such as stop(), start(), and restart(), and because we use a Swing
GUI in the first place. Additionally, our original customers pointed out that we have separate
getters for x, y, r, g, b, width, and height in our keyframe states, when getters for position,
color, and dimensions would have been more practical design.
