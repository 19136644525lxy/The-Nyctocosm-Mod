scoreboard objectives add aj.i dummy
scoreboard objectives add aj.id dummy
scoreboard objectives add aj.tween_time dummy
scoreboard objectives add aj.anim_time dummy
scoreboard objectives add aj.life_time dummy
scoreboard objectives add aj.loop_mode dummy
scoreboard objectives add aj.export_version dummy
scoreboard objectives add aj.test.rig_loaded dummy
scoreboard objectives add aj.animation.ez.local_anim_time dummy
scoreboard players set $aj.test.animation.ez aj.id 0
scoreboard players set $aj.test.variant.default aj.id 0
scoreboard players add .aj.last_id aj.id 0
scoreboard players set $aj.default_interpolation_duration aj.i 1
scoreboard players set $aj.loop_mode.loop aj.i 0
scoreboard players set $aj.loop_mode.once aj.i 1
scoreboard players set $aj.loop_mode.hold aj.i 2
scoreboard players set .aj.export_version aj.i 754850038
scoreboard players reset * aj.test.rig_loaded
execute as @e[type=#test:aj_root,tag=aj.test.root] run function #test:on_load