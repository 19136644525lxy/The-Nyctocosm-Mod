scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajsorcererm1.animation.shardhail.local_anim_time 0
scoreboard players set @s aj.ajsorcererm1.animation.shardhail.loop_mode 1
execute on passengers run data modify entity @s interpolation_duration set value 0
function zzz_ajsorcererm1_internal:animations/shardhail/tree/leaf_0
execute on passengers run data modify entity @s interpolation_duration set value 1
tag @s add aj.ajsorcererm1.animation.shardhail