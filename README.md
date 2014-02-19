Chunkster
=========

A chunk buying and selling system for Bukkit.

License:

CC by-nc-nd

http://creativecommons.org/licenses/by-nc-nd/4.0/


Commands:
  - /chunk
  - /unchunk
  - /chm flag [move/build/container/chat]    //pvp will be added soon
  - /chm remove owner/member username
  - /chm add owner/member username
  
Rights:
  - mainowner extends owner:
   - selling chunk
   - manage user owner/member
  
  - owner extends member:
   - modify flags
  
  - member:
   - build
   - chat
   - access container
   - move

v 0.9:
- each member/owner/mainowner has a personal flag
- trees can't overgrow. to other chunks
