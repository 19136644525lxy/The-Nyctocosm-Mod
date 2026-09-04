scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajemissary.animation.phase4.attack.grapple_grab.local_anim_time 0
scoreboard players set @s aj.ajemissary.animation.phase4.attack.grapple_grab.loop_mode 2
execute on passengers run data modify entity @s interpolation_duration set value 0
function animated_java:ajemissary/zzzzzzzz/animations/phase4.attack.grapple_grab/tree/leaf_0
execute on passengers run data modify entity @s interpolation_duration set value 1
tag @s add aj.ajemissary.animation.phase4.attack.grapple_grab