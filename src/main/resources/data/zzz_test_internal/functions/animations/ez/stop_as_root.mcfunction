scoreboard players set @s aj.animation.ez.local_anim_time 0
tag @s remove aj.test.animation.ez
execute on passengers run data modify entity @s interpolation_duration set value 0
function zzz_test_internal:animations/ez/tree/leaf_0