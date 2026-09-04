scoreboard players add @s aj.ajemissary.animation.phase1.attack.lightning_javelin.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajemissary.animation.phase1.attack.lightning_javelin.local_anim_time
function animated_java:ajemissary/zzzzzzzz/animations/phase1.attack.lightning_javelin/apply_frame_as_root
execute if score @s aj.ajemissary.animation.phase1.attack.lightning_javelin.local_anim_time matches 50.. run function animated_java:ajemissary/zzzzzzzz/animations/phase1.attack.lightning_javelin/end