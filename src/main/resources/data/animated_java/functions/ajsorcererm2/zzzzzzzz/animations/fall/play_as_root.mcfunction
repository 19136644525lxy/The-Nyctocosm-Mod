scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajsorcererm2.animation.fall.local_anim_time 0
scoreboard players set @s aj.ajsorcererm2.animation.fall.loop_mode 1
execute on passengers run data modify entity @s interpolation_duration set value 0
function animated_java:ajsorcererm2/zzzzzzzz/animations/fall/tree/leaf_0
execute on passengers run data modify entity @s interpolation_duration set value 1
tag @s add aj.ajsorcererm2.animation.fall