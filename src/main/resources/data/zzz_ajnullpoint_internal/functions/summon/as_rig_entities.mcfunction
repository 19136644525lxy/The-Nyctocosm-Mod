scoreboard players operation @s aj.id = .aj.last_id aj.id
tag @s remove aj.new
function #ajnullpoint:on_summon_as_rig_entities
execute if entity @s[tag=aj.ajnullpoint.bone] run function zzz_ajnullpoint_internal:summon/as_bones
execute if entity @s[tag=aj.ajnullpoint.locator] run function zzz_ajnullpoint_internal:summon/as_locators
execute if entity @s[tag=aj.ajnullpoint.camera] run function zzz_ajnullpoint_internal:summon/as_cameras