scoreboard objectives add aj.i dummy
scoreboard objectives add aj.id dummy
scoreboard objectives add aj.tween_time dummy
scoreboard objectives add aj.anim_time dummy
scoreboard objectives add aj.life_time dummy
scoreboard objectives add aj.loop_mode dummy
scoreboard objectives add aj.export_version dummy
scoreboard objectives add aj.inculcate.rig_loaded dummy
scoreboard objectives add aj.animation.inculcate.local_anim_time dummy
scoreboard players set $aj.inculcate.animation.inculcate aj.id 0
scoreboard players set $aj.inculcate.variant.default aj.id 0
scoreboard players add .aj.last_id aj.id 0
scoreboard players set $aj.default_interpolation_duration aj.i 1
scoreboard players set $aj.loop_mode.loop aj.i 0
scoreboard players set $aj.loop_mode.once aj.i 1
scoreboard players set $aj.loop_mode.hold aj.i 2
scoreboard players set .aj.export_version aj.i 21127963
scoreboard players reset * aj.inculcate.rig_loaded
execute as @e[type=#inculcate:aj_root,tag=aj.inculcate.root] run function #inculcate:on_load