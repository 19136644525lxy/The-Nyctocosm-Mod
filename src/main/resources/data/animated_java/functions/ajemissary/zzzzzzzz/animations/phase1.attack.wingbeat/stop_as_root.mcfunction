scoreboard players set @s aj.ajemissary.animation.phase1.attack.wingbeat.local_anim_time 0
tag @s remove aj.ajemissary.animation.phase1.attack.wingbeat
execute on passengers run data modify entity @s interpolation_duration set value 0
tag @s add aj.ajemissary.disable_command_keyframes
function animated_java:ajemissary/zzzzzzzz/animations/phase1.attack.wingbeat/tree/leaf_0
tag @s remove aj.ajemissary.disable_command_keyframes