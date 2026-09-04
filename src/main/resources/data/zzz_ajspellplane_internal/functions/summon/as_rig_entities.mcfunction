scoreboard players operation @s aj.id = .aj.last_id aj.id
tag @s remove aj.new
function #ajspellplane:on_summon_as_rig_entities
execute if entity @s[tag=aj.ajspellplane.bone] run function zzz_ajspellplane_internal:summon/as_bones
execute if entity @s[tag=aj.ajspellplane.locator] run function zzz_ajspellplane_internal:summon/as_locators
execute if entity @s[tag=aj.ajspellplane.camera] run function zzz_ajspellplane_internal:summon/as_cameras