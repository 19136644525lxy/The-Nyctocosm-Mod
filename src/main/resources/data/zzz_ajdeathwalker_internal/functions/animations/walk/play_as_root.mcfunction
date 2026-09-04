scoreboard players set @s aj.anim_time 0
scoreboard players set @s aj.ajdeathwalker.animation.walk.local_anim_time 0
scoreboard players set @s aj.ajdeathwalker.animation.walk.loop_mode 0
execute on passengers run data modify entity @s interpolation_duration set value 0
function zzz_ajdeathwalker_internal:animations/walk/tree/leaf_0
execute on passengers run data modify entity @s interpolation_duration set value 1
tag @s add aj.ajdeathwalker.animation.walk