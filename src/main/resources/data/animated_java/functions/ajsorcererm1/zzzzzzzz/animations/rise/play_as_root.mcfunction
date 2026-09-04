scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajsorcererm1.animation.rise.local_anim_time 0
scoreboard players set @s aj.ajsorcererm1.animation.rise.loop_mode 2
execute on passengers run data modify entity @s interpolation_duration set value 0
function animated_java:ajsorcererm1/zzzzzzzz/animations/rise/tree/leaf_0
execute on passengers run data modify entity @s interpolation_duration set value 1
tag @s add aj.ajsorcererm1.animation.rise